package com.thejohnsondev.ui.components.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.thejohnsondev.ui.designsystem.Size12
import com.thejohnsondev.ui.designsystem.Size2
import com.thejohnsondev.ui.designsystem.Size4
import kotlin.math.PI
import kotlin.math.atan2

object SimpleArrow : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(width, height * 0.5f)
            lineTo(0f, height)
            lineTo(width * 0.5f, height * 0.5f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun AnimatedArrowPointer(
    modifier: Modifier,
    color: Color,
    isVisible: Boolean = true,
    strokeWidth: Dp = Size2,
    pointerSize: Dp = Size12,
    dashLength: Dp? = Size4,
    strokeCap: StrokeCap = StrokeCap.Round,
    pointerShape: Shape = SimpleArrow,
    animationSpec: AnimationSpec<Float> = tween(1500)
) {
    val pathCompletion = remember { Animatable(0f) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            pathCompletion.animateTo(1f, animationSpec)
        } else {
            pathCompletion.snapTo(0f)
        }
    }

    Canvas(
        modifier.aspectRatio(0.9f)
    ) {
        val arrowPath = createArrowPath(size.width, size.height)

        val pathMeasure = PathMeasure().apply {
            setPath(arrowPath, false)
        }

        val pathSegment = Path()
        val stopDistance = pathCompletion.value * pathMeasure.length
        pathMeasure.getSegment(0f, stopDistance, pathSegment, true)

        drawPathSegment(pathSegment, color, strokeWidth, strokeCap, dashLength)

        if (pathCompletion.value > 0) {
            drawPointerHead(pathMeasure, stopDistance, pointerSize, color, pointerShape)
        }
    }
}

private fun createArrowPath(width: Float, height: Float): Path {
    return Path().apply {
        moveTo(width * 0.2f, 0f)
        cubicTo(
            x1 = 0f, y1 = height * 0.25f,
            x2 = width * 0.1f, y2 = height * 0.7f,
            x3 = width * 0.65f, y3 = height * 0.6f
        )
        cubicTo(
            x1 = width, y1 = height * 0.50f,
            x2 = width * 0.48f, y2 = height * 0.20f,
            x3 = width * 0.3f, y3 = height * 0.5f
        )
        cubicTo(
            x1 = width * 0.2f, y1 = height * 0.70f,
            x2 = width * 0.5f, y2 = height,
            x3 = width, y3 = height
        )
    }
}

private fun DrawScope.drawPathSegment(
    path: Path,
    color: Color,
    strokeWidth: Dp,
    strokeCap: StrokeCap,
    dashLength: Dp? = null
) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth.toPx(),
            cap = strokeCap,
            pathEffect = dashLength?.let { dash ->
                PathEffect.dashPathEffect(
                    floatArrayOf(dash.toPx(), dash.toPx())
                )
            }
        )
    )
}

fun DrawScope.drawPointerHead(
    pathMeasure: PathMeasure,
    stopDistance: Float,
    pointerSize: Dp,
    color: Color,
    pointerShape: Shape
) {
    val headPoint = pathMeasure.getPosition(stopDistance)
    val tangent = pathMeasure.getTangent(stopDistance)

    val angle = atan2(tangent.y.toDouble(), tangent.x.toDouble()).toFloat() * 180 / PI.toFloat()

    val headSize = Size(pointerSize.toPx(), pointerSize.toPx())
    val headOutline = pointerShape.createOutline(headSize, layoutDirection, this)

    translate(headPoint.x - (headSize.width / 2), headPoint.y - (headSize.height / 2)) {
        rotate(angle, pivot = headSize.center) {
            drawOutline(headOutline, color = color)
        }
    }
}
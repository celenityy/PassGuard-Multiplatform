package com.thejohnsondev.common.model.auth.firebase

import com.thejohnsondev.common.model.Error
import kotlinx.serialization.Serializable

@Serializable
data class FBErrorBody(
    val error: FirebaseApiErrorBody
): Error()

@Serializable
data class FirebaseApiErrorBody(
    val code: Int,
    val message: String
)
package com.thejohnsondev.common.model.tools

import com.thejohnsondev.common.model.DisplayableMessageValue

data class PasswordStrength(
    val level: Float,
    val suggestion: DisplayableMessageValue?
)
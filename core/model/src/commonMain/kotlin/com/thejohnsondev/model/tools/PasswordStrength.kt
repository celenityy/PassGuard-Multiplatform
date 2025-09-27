package com.thejohnsondev.model.tools

import com.thejohnsondev.model.DisplayableMessageValue

data class PasswordStrength(
    val level: Float,
    val suggestion: DisplayableMessageValue?
)
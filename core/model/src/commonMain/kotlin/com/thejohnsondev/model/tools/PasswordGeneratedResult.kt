package com.thejohnsondev.model.tools

import com.thejohnsondev.model.DisplayableMessageValue

data class PasswordGeneratedResult(
    val password: String,
    val strengthLevel: Float,
    val suggestion: DisplayableMessageValue?
)
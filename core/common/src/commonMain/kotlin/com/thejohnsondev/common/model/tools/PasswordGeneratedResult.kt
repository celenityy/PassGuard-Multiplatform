package com.thejohnsondev.common.model.tools

import com.thejohnsondev.common.model.DisplayableMessageValue

data class PasswordGeneratedResult(
    val password: String,
    val strengthLevel: Float,
    val suggestion: DisplayableMessageValue?
)
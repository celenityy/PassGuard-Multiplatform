package com.thejohnsondev.domain.repo

import com.thejohnsondev.common.model.tools.PasswordGeneratedResult
import com.thejohnsondev.common.model.tools.PasswordGenerationType
import com.thejohnsondev.common.model.tools.PasswordStrength

interface PasswordGenerationRepository {
    fun generatePassword(
        type: PasswordGenerationType,
        length: Int = 12,
        includeLower: Boolean = true,
        includeUpper: Boolean = true,
        includeDigits: Boolean = true,
        includeSpecial: Boolean = true,
    ): PasswordGeneratedResult

    fun evaluateStrength(password: String): PasswordStrength
    fun isCommonPassword(password: String): Boolean
}
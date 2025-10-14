package com.thejohnsondev.domain.repo

import com.thejohnsondev.common.model.tools.PasswordGeneratorConfig

interface ToolsRepository {
    suspend fun updatePasswordGeneratorConfig(
        config: PasswordGeneratorConfig
    )
    suspend fun getPasswordGeneratorConfig(): PasswordGeneratorConfig
    fun copyText(text: String, isSensitive: Boolean)
}
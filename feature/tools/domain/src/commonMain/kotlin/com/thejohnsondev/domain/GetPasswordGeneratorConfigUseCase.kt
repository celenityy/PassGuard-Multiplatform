package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.ToolsRepository
import com.thejohnsondev.common.model.tools.PasswordGeneratorConfig

class GetPasswordGeneratorConfigUseCase(
    private val toolsRepository: ToolsRepository,
) {
    suspend operator fun invoke(): PasswordGeneratorConfig {
        return toolsRepository.getPasswordGeneratorConfig()
    }
}
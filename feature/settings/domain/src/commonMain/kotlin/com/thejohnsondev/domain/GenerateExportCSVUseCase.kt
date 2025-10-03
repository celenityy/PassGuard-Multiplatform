package com.thejohnsondev.domain

import com.thejohnsondev.domain.export.CSVGenerationResult
import com.thejohnsondev.domain.export.CSVImportExportUtils
import com.thejohnsondev.common.model.vault.PasswordDto

class GenerateExportCSVUseCase {
    operator fun invoke(decryptedPasswords: List<PasswordDto>): CSVGenerationResult {
        val csvGenerationResult = CSVImportExportUtils.generateCsvContentForPasswords(decryptedPasswords)
        return csvGenerationResult
    }
}
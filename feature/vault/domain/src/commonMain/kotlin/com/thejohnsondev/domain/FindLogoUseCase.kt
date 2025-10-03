package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.domain.repo.VaultRepository
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.auth.logo.FindLogoResponse

class FindLogoUseCase(
    private val vaultRepository: VaultRepository
) {
    suspend operator fun invoke(query: String): Either<Error, List<FindLogoResponse>> {
        return vaultRepository.findLogo(query)
    }
}
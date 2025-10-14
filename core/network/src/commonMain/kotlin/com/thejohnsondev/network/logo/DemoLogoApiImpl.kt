package com.thejohnsondev.network.logo

import arrow.core.Either
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.auth.logo.FindLogoResponse

class DemoLogoApiImpl : LogoApi {
    override suspend fun find(query: String): Either<Error, List<FindLogoResponse>> {
        return Either.Right(listOf())
    }
}
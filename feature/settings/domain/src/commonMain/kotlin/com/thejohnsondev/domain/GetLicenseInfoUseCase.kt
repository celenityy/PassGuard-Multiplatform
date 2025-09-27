package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.domain.model.LicenseInfo

class GetLicenseInfoUseCase {
    operator fun invoke(): LicenseInfo {
        val logoProviderName = BuildKonfigProvider.getLogoProviderName()
        val logoProviderUrl = BuildKonfigProvider.getLogoProviderUrl()
        return LicenseInfo(
            logoProviderName = logoProviderName,
            logoProviderUrl = logoProviderUrl,
        )
    }
}
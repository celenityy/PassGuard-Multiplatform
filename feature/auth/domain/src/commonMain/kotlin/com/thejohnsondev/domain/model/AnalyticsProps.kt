package com.thejohnsondev.domain.model

import com.thejohnsondev.common.model.settings.DarkThemeConfig
import com.thejohnsondev.common.model.vault.VaultType

data class AnalyticsProps(
    val installID: String?,
    val isVaultInitialized: Boolean,
    val vaultType: VaultType?,
    val darkThemeConfig: DarkThemeConfig,
    val appVersion: String,
    val platform: String
)
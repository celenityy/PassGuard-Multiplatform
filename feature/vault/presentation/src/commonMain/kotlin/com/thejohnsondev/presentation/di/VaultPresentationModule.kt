package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.presentation.vault.VaultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vaultPresentationModule = module {
    viewModelOf(::VaultViewModel)
    viewModelOf(::AddVaultItemViewModel)
}
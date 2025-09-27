package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.presentation.exportv.ExportPasswordsViewModel
import com.thejohnsondev.presentation.exportv.NotExportedPasswordsViewModel
import com.thejohnsondev.presentation.importv.ImportPasswordsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ExportPasswordsViewModel)
    viewModelOf(::NotExportedPasswordsViewModel)
    viewModelOf(::ImportPasswordsViewModel)
}
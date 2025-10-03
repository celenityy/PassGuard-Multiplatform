package com.thejohnsondev.common.model.validation

import com.thejohnsondev.common.model.DisplayableMessageValue

sealed interface PasswordValidationState {
    data object PasswordCorrectState : PasswordValidationState
    data class PasswordIncorrectState(val reason: DisplayableMessageValue) : PasswordValidationState
}
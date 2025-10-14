package com.thejohnsondev.common.model.validation

import com.thejohnsondev.common.model.DisplayableMessageValue

sealed interface EmailValidationState {
    data object EmailCorrectState : EmailValidationState
    data class EmailIncorrectState(val reason: DisplayableMessageValue) : EmailValidationState
}
package com.thejohnsondev.presentation.onboarding

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.VAULT_GENERATION_FAKE_TIME_DURATION
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.repo.AuthService
import com.thejohnsondev.common.model.OneTimeEvent
import com.thejohnsondev.common.model.ScreenState
import com.thejohnsondev.common.model.vault.VaultType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class OnboardingViewModel(
    private val authService: AuthService
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(
        viewModelScope, SharingStarted.Eagerly,
        State()
    )

    fun perform(action: Action) {
        when (action) {
            is Action.CreateLocalVault -> createLocalVault()
            is Action.AcceptPrivacyPolicy -> acceptPrivacyPolicy(action.accepted)
        }
    }

    private fun createLocalVault() = launchLoading {
        authService.generateSecretKey()
        delay(VAULT_GENERATION_FAKE_TIME_DURATION)
        Analytics.setVaultType(VaultType.LOCAL.name)
        Analytics.trackEvent("created_local_vault")
        sendEvent(NavigateToHome)
    }

    private fun acceptPrivacyPolicy(accepted: Boolean) = launch {
        _state.update {
            it.copy(isPrivacyPolicyAccepted = accepted)
        }
    }

    data object NavigateToHome : OneTimeEvent()

    sealed class Action {
        data object CreateLocalVault : Action()
        data class AcceptPrivacyPolicy(val accepted: Boolean) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val isPrivacyPolicyAccepted: Boolean = false
    )
}
package com.thejohnsondev.common.model

sealed interface LoadingState {
    data object Loading : LoadingState
    data object Loaded : LoadingState
}
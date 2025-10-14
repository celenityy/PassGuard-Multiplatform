package com.thejohnsondev.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.common.utils.getFirebaseErrorMessage
import com.thejohnsondev.common.model.DisplayableMessageValue
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.HttpError
import com.thejohnsondev.common.model.InvalidTokenError
import com.thejohnsondev.common.model.NetworkError
import com.thejohnsondev.common.model.OneTimeEvent
import com.thejohnsondev.common.model.ScreenState
import com.thejohnsondev.common.model.UnknownError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    private val eventFlow = Channel<OneTimeEvent>()
    protected val screenState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.None)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            handleError(UnknownError(throwable))
        }
    }

    fun getEventFlow() = eventFlow.receiveAsFlow()

    protected suspend fun BaseViewModel.sendEvent(event: OneTimeEvent) {
        withContext(Dispatchers.Main) {
            eventFlow.send(event)
        }
    }

    protected suspend fun BaseViewModel.loading() {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.Loading)
        }
    }

    protected suspend fun BaseViewModel.showContent() {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.ShowContent)
        }
    }

    protected suspend fun showError(message: String) {
        withContext(Dispatchers.Main) {
            screenState.emit(ScreenState.Error(message))
        }
    }

    protected suspend fun handleError(error: Error) {
        withContext(Dispatchers.Main) {
            showContent()
            val errorDisplayMessage = when (error) {
                is HttpError -> DisplayableMessageValue.StringValue(
                    getFirebaseErrorMessage(error.message)
                )

                is NetworkError -> DisplayableMessageValue.CheckInternetConnection
                is UnknownError -> DisplayableMessageValue.StringValue(
                    error.throwable?.message ?: "Unknown error"
                )

                else -> DisplayableMessageValue.StringValue(error.throwable?.message ?: "Unknown error")
            }
            Logger.e("${this::class.simpleName} error: ${error.throwable?.stackTraceToString()} -- $errorDisplayMessage")
            sendEvent(OneTimeEvent.ErrorMessage(errorDisplayMessage))
        }
    }

    protected fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            block.invoke(viewModelScope)
        }
        return job
    }

    protected fun BaseViewModel.launchLoading(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(coroutineExceptionHandler) {
            loading()
            block.invoke(viewModelScope)
        }
        return job
    }

    protected suspend fun <T> Either<Error, T>.onResult(
        onError: ((Error) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) = fold(
        ifLeft = { error -> onError?.invoke(error) ?: handleError(error) },
        ifRight = { result -> onSuccess(result) }
    )

    protected suspend fun <T> Flow<Either<Error, T>>.onResult(
        onError: ((Error) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit
    ) = first().fold(
        ifLeft = { error -> onError?.invoke(error) ?: handleError(error) },
        ifRight = { result -> onSuccess(result) }
    )

    protected inline fun <reified E : Error, reified T> BaseViewModel.makeApiCall(
        noinline call: suspend () -> Flow<Either<E, T>>,
        noinline onError: ((Error) -> Unit)? = null,
        noinline refreshToken: (suspend () -> Unit)? = null,
        noinline onSuccess: (T) -> Unit
    ) = launch {
        call().first().fold(
            ifLeft = { error ->
                if (error is InvalidTokenError) {
                    // refresh token
                    refreshToken?.invoke()
                    call().onResult(
                        onError = onError,
                        onSuccess = onSuccess
                    )
                } else {
                    onError?.invoke(error) ?: handleError(error)
                }
            },
            ifRight = { result -> onSuccess(result) }
        )
    }

}
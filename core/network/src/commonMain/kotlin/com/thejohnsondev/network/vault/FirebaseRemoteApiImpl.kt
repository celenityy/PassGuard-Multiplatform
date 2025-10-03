package com.thejohnsondev.network.vault

import arrow.core.Either
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.auth.firebase.FBApiKey
import com.thejohnsondev.common.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.common.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.common.model.auth.firebase.FBRefreshTokenRequestBody
import com.thejohnsondev.common.model.auth.firebase.FBRefreshTokenResponseBody
import com.thejohnsondev.common.model.vault.PasswordDto
import com.thejohnsondev.network.callWithMapping
import com.thejohnsondev.network.defaultRequestConfig
import com.thejohnsondev.network.defaultUrlConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class FirebaseRemoteApiImpl(
    private val client: HttpClient,
    private val fbApiKey: FBApiKey,
    private val preferencesDataStore: PreferencesDataStore
) : RemoteApi {
    override suspend fun signUp(
        body: FBAuthRequestBody
    ): Either<Error, FBAuthSignUpResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_SIGN_UP)
                    parameters.append(QUERY_KEY, fbApiKey.key)
                }
                setBody(body)
            }
        }
    }

    override suspend fun signIn(
        body: FBAuthRequestBody
    ): Either<Error, FBAuthSignInResponse> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_LOGIN)
                    parameters.append(QUERY_KEY, fbApiKey.key)
                }
                setBody(body)
            }
        }
    }

    override suspend fun deleteAccount(body: FBAuthDeleteAccountBody): Either<Error, Unit> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_DELETE_ACCOUNT)
                    parameters.append(QUERY_KEY, fbApiKey.key)
                }
                setBody(body.copy(idToken = preferencesDataStore.getAuthToken()))
            }
        }
    }

    override suspend fun refreshToken(body: FBRefreshTokenRequestBody): Either<Error, FBRefreshTokenResponseBody> {
        return callWithMapping {
            client.post {
                defaultRequestConfig()
                url {
                    defaultUrlConfig()
                    path(FIREBASE_REFRESH_TOKEN)
                    parameters.append(QUERY_KEY, fbApiKey.key)
                }
                setBody(body)
            }
        }
    }

    override suspend fun createPassword(passwordDto: PasswordDto): Either<Error, Unit> {
        return Either.Right(Unit) // TODO NOT IMPLEMENTED
    }

    override suspend fun getPasswords(): Either<Error, List<PasswordDto>> {
        return Either.Right(emptyList()) // TODO NOT IMPLEMENTED
    }

    override suspend fun updatePassword(passwordDto: PasswordDto): Either<Error, Unit> {
        return Either.Right(Unit) // TODO NOT IMPLEMENTED
    }

    override suspend fun deletePassword(passwordID: String): Either<Error, Unit> {
        return Either.Right(Unit) // TODO NOT IMPLEMENTED
    }
}
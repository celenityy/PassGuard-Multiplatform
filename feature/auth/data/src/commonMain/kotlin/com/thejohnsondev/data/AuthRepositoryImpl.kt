package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.domain.repo.AuthRepository
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.common.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.common.model.auth.firebase.FBRefreshTokenRequestBody
import com.thejohnsondev.common.model.auth.firebase.FBRefreshTokenResponseBody
import com.thejohnsondev.common.model.auth.firebase.GRAND_TYPE_REFRESH
import com.thejohnsondev.common.model.settings.DarkThemeConfig
import com.thejohnsondev.network.vault.RemoteApi
import com.thejosnsondev.biometric.BiometricAuthResult
import com.thejosnsondev.biometric.BiometricAuthenticator
import com.thejosnsondev.biometric.BiometricAvailability
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val remoteApi: RemoteApi,
    private val biometricAuthenticator: BiometricAuthenticator
) : AuthRepository {

    override suspend fun signUp(
        body: FBAuthRequestBody
    ): Flow<Either<Error, FBAuthSignUpResponse>> {
        return flowOf(remoteApi.signUp(body))
    }

    override suspend fun singIn(
        body: FBAuthRequestBody
    ): Flow<Either<Error, FBAuthSignInResponse>> {
        return flowOf(remoteApi.signIn(body))
    }

    override suspend fun signOut() {
        localDataSource.logout()
        preferencesDataStore.clearUserData()
    }

    override suspend fun isVaultInitialized(): Boolean {
        return preferencesDataStore.isVaultInitialized()
    }

    override suspend fun isVaultLocal(): Boolean {
        return preferencesDataStore.isVaultLocal()
    }

    override suspend fun deleteAccount(): Flow<Either<Error, Unit>> {
        val token = preferencesDataStore.getAuthToken()
        val body = FBAuthDeleteAccountBody(token)
        return flowOf(remoteApi.deleteAccount(body))
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return flowOf(Either.Right(true))
    }

    override suspend fun saveAuthToken(token: String) {
        preferencesDataStore.saveAuthToken(token)
    }

    override suspend fun saveEmail(email: String) {
        preferencesDataStore.saveEmail(email)
    }

    override suspend fun getUserEmail(): String? {
        return preferencesDataStore.getEmail()
    }

    override suspend fun saveRefreshAuthToken(refreshAuthToken: String) {
        preferencesDataStore.saveRefreshAuthToken(refreshAuthToken)
    }

    override suspend fun refreshToken(): Flow<Either<Error, FBRefreshTokenResponseBody>> {
        return flowOf(
            remoteApi.refreshToken(
                body = FBRefreshTokenRequestBody(
                    grantType = GRAND_TYPE_REFRESH,
                    refreshToken = preferencesDataStore.getRefreshAuthToken()
                )
            )
        )
    }

    override suspend fun isUseBiometrics(): Boolean {
        return preferencesDataStore.getSettingsConfigFlow()
            .first().privacySettings.isUnlockWithBiometricEnabled
    }

    override suspend fun showBiometricPrompt(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult {
        return biometricAuthenticator.authenticate(
            promptTitle = promptTitle,
            promptSubtitle = promptSubtitle,
            promptDescription = promptDescription
        )
    }

    override suspend fun getBiometricAvailability(): BiometricAvailability {
        return biometricAuthenticator.getBiometricAvailability()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun generateAndSaveInstallId() {
        val installID = Uuid.random().toString()
        preferencesDataStore.saveInstallId(installID)
    }

    override suspend fun getInstallId(): String? {
        return preferencesDataStore.getInstallId()
    }

    override suspend fun getDarkThemeConfig(): DarkThemeConfig {
        return preferencesDataStore.getSettingsConfigFlow().first().darkThemeConfig
    }

}
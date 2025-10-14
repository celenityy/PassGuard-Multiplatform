package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.domain.repo.AuthService
import com.thejohnsondev.domain.repo.AuthRepository
import com.thejohnsondev.domain.repo.EncryptionRepository
import com.thejohnsondev.domain.repo.GenerateKeyRepository
import com.thejohnsondev.common.model.Error
import com.thejohnsondev.common.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.common.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.common.model.auth.firebase.FBRefreshTokenResponseBody
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val generateKeyRepository: GenerateKeyRepository,
    private val encryptionRepository: EncryptionRepository
) : AuthService {

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignInResponse>> {
        val requestBody = FBAuthRequestBody(email, password, true)
        return authRepository.singIn(requestBody)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignUpResponse>> {
        val requestBody = FBAuthRequestBody(email, password, true)
        return authRepository.signUp(requestBody)
    }

    override suspend fun logout() {
        authRepository.signOut()
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return authRepository.changePassword(oldPassword, newPassword)
    }

    override suspend fun deleteAccount(): Flow<Either<Error, Unit>> {
        return authRepository.deleteAccount()
    }

    override suspend fun generateSecretKey() {
        val secretKey = generateKeyRepository.generateSecretKey()
        encryptionRepository.saveSecretKey(secretKey)
    }

    override suspend fun saveAuthToken(authToken: String) {
        authRepository.saveAuthToken(authToken)
    }

    override suspend fun saveRefreshAuthToken(refreshAuthToken: String) {
        authRepository.saveRefreshAuthToken(refreshAuthToken)
    }

    override suspend fun saveEmail(email: String) {
        authRepository.saveEmail(email)
    }

    override suspend fun refreshToken(): Flow<Either<Error, FBRefreshTokenResponseBody>> {
        return authRepository.refreshToken()
    }

    override suspend fun isVaultLocal(): Boolean {
        return authRepository.isVaultLocal()
    }

}
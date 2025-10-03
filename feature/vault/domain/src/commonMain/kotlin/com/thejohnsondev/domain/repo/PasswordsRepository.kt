package com.thejohnsondev.domain.repo

import com.thejohnsondev.common.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

interface PasswordsRepository {
    suspend fun getUserPasswords(): Flow<List<PasswordDto>>
    suspend fun createOrUpdatePassword(passwordDto: PasswordDto)
    suspend fun updatePasswords(passwordList: List<PasswordDto>)
    suspend fun deletePassword(passwordId: String)
    suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean)
}
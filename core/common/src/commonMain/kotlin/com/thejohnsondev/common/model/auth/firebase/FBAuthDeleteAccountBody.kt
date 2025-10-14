package com.thejohnsondev.common.model.auth.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FBAuthDeleteAccountBody(
    val idToken: String
)
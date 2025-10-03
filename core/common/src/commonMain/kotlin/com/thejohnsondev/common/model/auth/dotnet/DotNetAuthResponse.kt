package com.thejohnsondev.common.model.auth.dotnet

import kotlinx.serialization.Serializable

@Serializable
data class DotNetAuthResponse(
    val token: String
)

package com.app.family_budget.data.remote.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class OwnerResponse(
    @SerialName("login")
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)

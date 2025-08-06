package com.affirmation.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileModel(
    val firstName: String? = null,
    val lastName: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    val email: String
)
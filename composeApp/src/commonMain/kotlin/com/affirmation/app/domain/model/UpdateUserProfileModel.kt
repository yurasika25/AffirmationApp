package com.affirmation.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProfileModel(
    val firstName: String,
    val lastName: String,
    val age: String,
    val phoneNumber: String,
    val email: String,
    val gender: String
)
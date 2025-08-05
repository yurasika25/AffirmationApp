package com.affirmation.app.data.network

import com.affirmation.app.domain.model.AffirmationData
import com.affirmation.app.domain.model.NotificationData
import com.affirmation.app.domain.model.UserProfile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class ApiService(val client: HttpClient) {
    suspend fun getAffirmationList(): List<AffirmationData> {
        return client.get("${BaseUrl.BASE_URL}/affirmations").body()
    }

    suspend fun getNotificationList(): List<NotificationData> {
        return client.get("${BaseUrl.BASE_URL}/notifications").body()
    }

    suspend fun getUserProfile(): UserProfile {
        val jsonString = client.get("${BaseUrl.BASE_URL}/user/profile").bodyAsText()
        return Json.decodeFromString(jsonString)
    }
}
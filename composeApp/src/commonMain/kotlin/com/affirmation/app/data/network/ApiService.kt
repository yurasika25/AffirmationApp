package com.affirmation.app.data.network

import com.affirmation.app.data.network.BaseUrl.BASE_URL
import com.affirmation.app.domain.model.AffirmationData
import com.affirmation.app.domain.model.NotificationModel
import com.affirmation.app.domain.model.UpdateUserProfileModel
import com.affirmation.app.domain.model.UserProfileModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class ApiService(val client: HttpClient) {

    suspend fun getUserDetails(): UpdateUserProfileModel {
        val jsonString = client.get("${BASE_URL}/user/details").bodyAsText()
        return Json.decodeFromString(jsonString)
    }

    suspend fun getAffirmationList(): List<AffirmationData> {
        return client.get("${BASE_URL}/affirmations").body()
    }

    suspend fun updateUserProfile(profile: UpdateUserProfileModel): Boolean {
        return try {
            val response = client.put("$BASE_URL/user/profile/update") {
                contentType(ContentType.Application.Json)
                setBody(profile)
            }
            response.status.value in 200..299
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getNotificationList(): List<NotificationModel> {
        return client.get("${BASE_URL}/notifications").body()
    }

    suspend fun getUserProfile(): UserProfileModel {
        val jsonString = client.get("${BASE_URL}/user/profile").bodyAsText()
        return Json.decodeFromString(jsonString)
    }
}
package com.affirmation.app.data.network

import com.affirmation.app.domain.model.AffirmationData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class ApiService(val client: HttpClient) {
    suspend fun getAffirmationList(): List<AffirmationData> {
        return client.get("${BaseUrl.BASE_URL}/affirmations").body()
    }

    suspend fun getUserProfile(): String {
        return client.get("${BaseUrl.BASE_URL}/user/profile").bodyAsText()
    }
}
package com.affirmation.app.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affirmation.app.data.network.ApiService
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.affirmation.app.domain.model.NotificationModel

class NotificationsViewModel(private val apiService: ApiService) : ViewModel() {

    var data by mutableStateOf<List<NotificationModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadData() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                data = apiService.getNotificationList()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}

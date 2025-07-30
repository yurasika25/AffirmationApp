package com.affirmation.app.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affirmation.app.data.network.ApiService
import com.affirmation.app.domain.model.AffirmationData
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class FavoriteViewModel(private val apiService: ApiService) : ViewModel() {

    var data by mutableStateOf<List<AffirmationData>>(emptyList())
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
                data = apiService.getAffirmationList()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }
}

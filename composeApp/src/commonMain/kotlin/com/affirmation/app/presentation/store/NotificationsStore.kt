package com.affirmation.app.presentation.store

import com.affirmation.app.data.network.ApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class NotificationsStore(
    private val api: ApiService
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _state = MutableStateFlow(NotificationsState())
    val state: StateFlow<NotificationsState> = _state.asStateFlow()

    fun dispatch(intent: NotificationsIntent) {
        when (intent) {
            NotificationsIntent.OnAppear -> loadData()
            NotificationsIntent.OnRefresh -> load(force = true)
        }
    }

    private fun loadData() {
        if (_state.value.items.isNotEmpty() || _state.value.isLoading) return
        load(force = false)
    }

    private fun load(force: Boolean) {
        if (_state.value.isLoading) return
        if (!force && _state.value.items.isNotEmpty()) return

        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val result = api.getNotificationList()
                _state.update { it.copy(isLoading = false, items = result, error = null) }
            } catch (t: Throwable) {
                val msg = t.message ?: "Unknown error"
                _state.update { it.copy(isLoading = false, error = msg) }
            }
        }
    }

    fun clear() {
        scope.cancel()
    }
}
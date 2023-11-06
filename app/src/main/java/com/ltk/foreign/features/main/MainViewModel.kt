package com.ltk.foreign.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _finishActivity = MutableStateFlow(false)
    val finishActivity: StateFlow<Boolean> = _finishActivity.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1_000L)
            _loading.emit(false)
        }
    }
}

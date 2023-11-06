package com.ltk.foreign.features.settings.viewmodel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ltk.foreign.data.db.ForeignDatabase
import com.ltk.foreign.data.model.Word
import com.ltk.foreign.data.repository.ImportRepository
import com.ltk.foreign.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    application: Application,
    private val settingsRepository: DataRepository,
) : AndroidViewModel(application) {

    private val importRepository = ImportRepository(
        provider = ForeignDatabase.getInstance(application),
        context = application,
        mutex = Mutex(),
        scope = viewModelScope,
        dispatcher = Dispatchers.IO
    )
    var words by mutableStateOf(emptyList<Word>())

    private var observeWordJob: Job? = null

    init {
        observe()
    }

    fun onImport(uri: Uri) {
        viewModelScope.launch {
            importRepository.import(uri)
            observe()
        }
    }

    private fun observe() {
        observeWords()
    }

    private fun observeWords() {
        observeWordJob?.cancel()
        observeWordJob = viewModelScope.launch {
            settingsRepository.getAllWords().collect { wordList ->
                words = wordList
            }
        }
    }
}

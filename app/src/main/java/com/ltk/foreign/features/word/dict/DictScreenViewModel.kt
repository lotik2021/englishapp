package com.ltk.foreign.features.word.dict

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ltk.foreign.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictScreenViewModel @Inject constructor(
    application: Application,
    private val dataRepository: DataRepository,
) : AndroidViewModel(application) {

    var listOfWord = dataRepository.getAllWords()

}

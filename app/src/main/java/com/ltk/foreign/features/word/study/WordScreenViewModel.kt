package com.ltk.foreign.features.word.study

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word
import com.ltk.foreign.data.repository.DataRepository
import com.ltk.foreign.di.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordScreenViewModel @Inject constructor(
    application: Application,
    private val dataRepository: DataRepository,
    private val dataStoreUtil: DataStoreUtil
) : AndroidViewModel(application) {

    private val dataStore = dataStoreUtil.dataStore

    lateinit var _dict: Dict

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _dict = dataRepository.getCurrentDict()
        }
    }

    var listOfDict = dataRepository.getAllDicts()

    private val _wordModel = MutableLiveData(Word(0, "<start>", "", "", null, 0, 0, 0))
    val wordModel: LiveData<Word> get() = _wordModel

    private val _dictModel = MutableLiveData(Dict(0, "<start>", "", 0, 0, 0))
    val dictModel: LiveData<Dict> get() = _dictModel

    fun loadWord() = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.loadWord(_dict.id, _dict.wordId).collect { response ->
            _wordModel.postValue(response)
        }
    }

    fun loadNextWord() = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.getNextWord(_dict.id, _dict.wordId).collect { response ->
            _wordModel.postValue(response)
        }
    }

    fun loadDict() = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.loadDict(_dict.id).collect { response ->
            _dict = response
            _dictModel.postValue(response)
        }
    }

    fun loadNewDict(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.loadNewDict(id).collect { response ->
            _dict = response
            _dictModel.postValue(response)
            loadWord()
        }
    }
}

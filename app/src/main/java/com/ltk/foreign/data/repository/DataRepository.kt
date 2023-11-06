package com.ltk.foreign.data.repository

import android.app.Application
import androidx.room.Transaction
import com.ltk.foreign.data.dao.DictDao
import com.ltk.foreign.data.dao.WordDao
import com.ltk.foreign.data.db.ForeignDatabase
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(application: Application) {

    private var wordDao: WordDao
    private var dictDao: DictDao

    init {
        val database = ForeignDatabase.getInstance(application)
        wordDao = database.wordDao()
        dictDao = database.dictDao()
    }

    fun getAllWords(): Flow<List<Word>> = wordDao.getAllWord()

    suspend fun deleteWordFromRoom(word: Word) {
        wordDao.deleteWord(word)
    }

    fun loadWord(dictId: Long, wordId: Long): Flow<Word> {
        return wordDao.getWord(wordId)
    }

    fun getNextWord(dictId: Long, wordId: Long): Flow<Word> {
        return flow {
            var word: Word
            if (wordId == 0L) {
                word = wordDao.getFirstWordByDict(dictId)
            } else {
                word = wordDao.getNextWordByDict(dictId, wordId)
            }
            if (word == null) {
                word = wordDao.getFirstWordByDict(dictId)
            }
            dictDao.setCurrentDictWord(dictId, word.id)
            emit(word)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun clear() {
        wordDao.clear()
    }

    fun loadDict(dictId: Long): Flow<Dict> {
        return dictDao.loadDict(dictId)
    }

    fun loadNewDict(dictId: Long): Flow<Dict> {
        dictDao.resetCurrentDict()
        dictDao.setCurrentDict(dictId)
        return dictDao.loadDict(dictId)
    }

    fun getAllDicts() = dictDao.getDicts()

    @Transaction
    fun getCurrentDict(): Dict {
        var dict: Dict? = dictDao.getCurrentDict()
        if (dict == null) {
            dict = dictDao.getFirstDict()
        }
        var wordId = dict.wordId
        if (wordId == 0L) {
            wordId = wordDao.getFirstWordIdByDict(dict.id)
        }

        if (dict.current != 1) {
            dictDao.resetCurrentDict()
            dictDao.setCurrentDictWord(dict.id, wordId)
        }
        return dict
    }
}

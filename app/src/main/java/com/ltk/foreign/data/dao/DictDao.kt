package com.ltk.foreign.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface DictDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDict(dict: Dict): Long

    @Query("SELECT * FROM dict ORDER BY title")
    fun getDicts(): Flow<List<Dict>>

    @Query("SELECT * FROM dict ORDER BY title LIMIT 1")
    fun getFirstDict(): Dict

    @Query("SELECT * FROM dict WHERE id = :id")
    fun loadDict(id: Long): Flow<Dict>

    @Query("SELECT * FROM dict WHERE is_current != 0 LIMIT 1")
    fun getCurrentDict(): Dict

    @Query("UPDATE dict SET is_current = 0")
    fun resetCurrentDict()

    @Query("UPDATE dict SET is_current = 1, word_id = :wordId WHERE id = :id")
    fun setCurrentDictWord(id: Long, wordId: Long)

    @Query("UPDATE dict SET is_current = 1 WHERE id = :id")
    fun setCurrentDict(id: Long)
}
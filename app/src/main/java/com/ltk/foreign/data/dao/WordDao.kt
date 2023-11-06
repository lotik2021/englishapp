package com.ltk.foreign.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltk.foreign.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word ORDER BY name ASC")
    fun getAllWord(): Flow<List<Word>>

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM word WHERE inside != 0")
    fun deleteOutsideWord()

    @Query("DELETE FROM word")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word): Long

    @Query("SELECT * FROM word WHERE id > :wordId AND dict_id = :dictId ORDER BY id LIMIT 1")
    fun getNextWordByDict(dictId: Long, wordId: Long): Word

    @Query("SELECT * FROM word WHERE dict_id = :dictId ORDER BY id LIMIT 1")
    fun getFirstWordByDict(dictId: Long): Word

    @Query("SELECT id FROM word WHERE dict_id = :dictId ORDER BY id LIMIT 1")
    fun getFirstWordIdByDict(dictId: Long): Long

    @Query("SELECT * FROM word WHERE id = :id")
    fun getWord(id: Long): Flow<Word>

}
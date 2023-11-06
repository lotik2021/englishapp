package com.ltk.foreign.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "word",
    indices = [Index(value = ["name"], name = "idx_word_name", unique = true)]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "transcription")
    val transcription: String?,

    @ColumnInfo(name = "translate")
    val translate: String,

    @ColumnInfo(name = "term")
    val term: String?,

    @ColumnInfo(name = "checked")
    val checked: Int,

    @ColumnInfo(name = "dict_id")
    val dictId: Long,

    @ColumnInfo(name = "inside")
    val inside: Int,
) : Parcelable

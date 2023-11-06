package com.ltk.foreign.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ltk.foreign.data.utils.DateTypeConverter
import com.ltk.foreign.data.dao.DictDao
import com.ltk.foreign.data.dao.WordDao
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word

@Database(entities = [Word::class, Dict::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class ForeignDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun dictDao(): DictDao

    companion object {
        @Suppress("ktlint:standard:property-naming")
        private var INSTANCE: ForeignDatabase? = null
        fun getInstance(context: Context): ForeignDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ForeignDatabase::class.java,
                        "foreign_db.sqlite"
                    )
                        .createFromAsset("db/foreign_db.sqlite")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

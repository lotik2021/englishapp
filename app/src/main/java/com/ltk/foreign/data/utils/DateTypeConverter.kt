package com.ltk.foreign.data.utils

import androidx.room.TypeConverter
import java.util.Date

object DateTypeConverter {
    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let {
            Date(it)
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}

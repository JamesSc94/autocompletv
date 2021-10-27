package com.jamessc94.autocompletetvj.Util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class converters_date {

    private val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH)

    @TypeConverter
    fun toDateTime(value: String?): Date? {
        return value?.let {
            return formatter.parse(value)

        }

    }

    @TypeConverter
    fun fromDateTime(date: Date?): String? {
        return formatter.format(date)

    }

}
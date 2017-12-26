package com.vertice.teepop.leaveapp.data.entity.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by VerDev06 on 12/26/2017.
 */
class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
package com.vertice.teepop.leaveapp.data.entity.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.vertice.teepop.leaveapp.data.model.Approved


/**
 * Created by VerDev06 on 1/8/2018.
 */
class ListApprovesConverter {

    @TypeConverter
    fun toListApproves(json: String): List<Approved>? {
        return Gson().fromJson(json, Array<Approved>::class.java).toList()
    }

    @TypeConverter
    fun toJsonSchema(approves: List<Approved>): String? {
        return Gson().toJson(approves)
    }
}
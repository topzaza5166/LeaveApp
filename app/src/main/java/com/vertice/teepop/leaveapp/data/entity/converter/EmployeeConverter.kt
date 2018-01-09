package com.vertice.teepop.leaveapp.data.entity.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.vertice.teepop.leaveapp.data.model.Employee

/**
 * Created by VerDev06 on 1/8/2018.
 */
class EmployeeConverter {

    @TypeConverter
    fun toEmployee(json: String?): Employee? {
        return Gson().fromJson(json, Employee::class.java)
    }

    @TypeConverter
    fun toJsonSchema(employee: Employee?): String? {
        return Gson().toJson(employee)
    }
}
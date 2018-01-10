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
        json?.let {
            return Gson().fromJson(it, Employee::class.java)
        }
        return Employee()
    }

    @TypeConverter
    fun toJsonSchema(employee: Employee?): String? {
        employee?.let {
            return Gson().toJson(it)
        }
        return ""
    }
}
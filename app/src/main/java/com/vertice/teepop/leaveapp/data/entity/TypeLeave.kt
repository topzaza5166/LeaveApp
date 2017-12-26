package com.vertice.teepop.leaveapp.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.vertice.teepop.leaveapp.data.entity.converter.DateConverter
import java.util.*

/**
 * Created by VerDev06 on 12/26/2017.
 */
@Entity(tableName = "Type_Leave")
class TypeLeave {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "typeName")
    @SerializedName("typeName")
    lateinit var typeName: String

    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    @TypeConverters(DateConverter::class)
    lateinit var createdDate: Date
}
package com.vertice.teepop.leaveapp.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.vertice.teepop.leaveapp.data.entity.converter.DateConverter
import java.util.*

/**
 * Created by VerDev06 on 12/27/2017.
 */
@Entity(tableName = "Leave")
class Leave {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    var userId: Int = 0

    @ColumnInfo(name = "typeId")
    @SerializedName("typeId")
    var typeId: Int = 0

    @ColumnInfo(name = "leaveDate")
    @SerializedName("leaveDate")
    @TypeConverters(DateConverter::class)
    lateinit var leaveDate: Date

    @ColumnInfo(name = "fromDate")
    @SerializedName("fromDate")
    @TypeConverters(DateConverter::class)
    lateinit var fromDate: Date

    @ColumnInfo(name = "toDate")
    @SerializedName("toDate")
    @TypeConverters(DateConverter::class)
    lateinit var toDate: Date

    @ColumnInfo(name = "timeLate")
    @SerializedName("timeLate")
    var timeLate: Int = 0

    @ColumnInfo(name = "reason")
    @SerializedName("reason")
    lateinit var reason: String

    @ColumnInfo(name = "approve")
    @SerializedName("approve")
    var approve: Int = 0

    @ColumnInfo(name = "leaveStatus")
    @SerializedName("leaveStatus")
    var leaveStatus: Int = 0

    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    @TypeConverters(DateConverter::class)
    lateinit var createdDate: Date
}
package com.vertice.teepop.leaveapp.data.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.vertice.teepop.leaveapp.data.entity.converter.DateConverter
import com.vertice.teepop.leaveapp.data.entity.converter.EmployeeConverter
import com.vertice.teepop.leaveapp.data.entity.converter.ListApprovesConverter
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.Employee
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by VerDev06 on 12/27/2017.
 */
@Entity(tableName = "Leave")
class Leave {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Long = 0

    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    var userId: Long = 0

    @ColumnInfo(name = "typeId")
    @SerializedName("typeId")
    var typeId: Int = 0

    @ColumnInfo(name = "leaveDate")
    @SerializedName("leaveDate")
    @TypeConverters(DateConverter::class)
    var leaveDate: Date = Date()

    @ColumnInfo(name = "fromDate")
    @SerializedName("fromDate")
    @TypeConverters(DateConverter::class)
    var fromDate: Date = Date()

    @ColumnInfo(name = "toDate")
    @SerializedName("toDate")
    @TypeConverters(DateConverter::class)
    var toDate: Date = Date()

    @ColumnInfo(name = "timeLate")
    @SerializedName("timeLate")
    var timeLate: Int = 0

    @ColumnInfo(name = "reason")
    @SerializedName("reason")
    var reason: String = ""

    @ColumnInfo(name = "approve")
    @SerializedName("approve")
    var approve: Int = 0

    @ColumnInfo(name = "leaveStatus")
    @SerializedName("leaveStatus")
    var leaveStatus: Int = 0

    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    @TypeConverters(DateConverter::class)
    var createdDate: Date = Date()

    @ColumnInfo(name = "leaveDays")
    @SerializedName("leaveDays")
    var leaveDays: Int = 0

    @ColumnInfo(name = "userInfo")
    @SerializedName("userInfo")
    @TypeConverters(EmployeeConverter::class)
    var userInfo: Employee? = null

    @ColumnInfo(name = "approves")
    @SerializedName("approves")
    @TypeConverters(ListApprovesConverter::class)
    var approves: List<Approved> = ArrayList()

    override fun toString(): String {
        return "ID: $id \n " +
                "UserId: $userId \n " +
                "TypeId: $typeId \n " +
                "LeaveDate: $leaveDate \n " +
                "FromDate: $fromDate \n " +
                "ToDate: $toDate \n " +
                "TimeLate: $timeLate \n " +
                "Reason: $reason \n " +
                "Approve $approve \n " +
                "LeaveStatus $leaveStatus \n " +
                "CreateDate $createdDate \n" +
                "userInfo $userInfo \n" +
                "approves ${approves.size}"
    }
}
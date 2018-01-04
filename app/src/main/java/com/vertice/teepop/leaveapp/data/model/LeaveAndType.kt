package com.vertice.teepop.leaveapp.data.model

import android.arch.persistence.room.*

import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave

import java.util.*

/**
 * Created by topza on 12/27/2017.
 */
class LeaveAndType {

    @Embedded
    lateinit var leave: Leave

    @Relation(parentColumn = "typeId", entityColumn = "id", entity = TypeLeave::class)
    lateinit var type: List<TypeLeave>

    fun dateDifferent(): String {
        val fromCalendar = Calendar.getInstance().apply { time = leave.fromDate }
        val toCalendar = Calendar.getInstance().apply { time = leave.toDate }

        val diff = ((toCalendar.timeInMillis - fromCalendar.timeInMillis) / (24 * 60 * 60 * 1000)) + 1
        return "Total : $diff Days"
    }

    fun formatTimeLate(): String {
        return "Time Late : ${leave.timeLate / 60} Hour ${leave.timeLate % 60} Minute"
    }
}
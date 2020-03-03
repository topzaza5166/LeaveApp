package com.vertice.teepop.leaveapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.airbnb.lottie.L
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.data.model.LeaveAndType


/**
 * Created by VerDev06 on 12/25/2017.
 */
interface LeaveRepository {

    fun getTypeLeave(): LiveData<List<TypeLeave>>

    fun postLeave(leave: Leave)

    fun getAllLeave(): LiveData<List<Leave>>

    fun getLeaveByUserId(id: Long): DataSource.Factory<Int, LeaveAndType>

    fun getAllLeaveAndType(): DataSource.Factory<Int, LeaveAndType>

    fun postApproves(approves: List<Approved>)

    fun postApprove(approve: Approved)

    fun deleteLeave(id: Long)
}
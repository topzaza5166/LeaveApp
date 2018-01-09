package com.vertice.teepop.leaveapp.data

import android.arch.lifecycle.LiveData
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.LeaveAndType


/**
 * Created by VerDev06 on 12/25/2017.
 */
interface LeaveRepository {

    fun getTypeLeave(): LiveData<List<TypeLeave>>

    fun postLeave(leave: Leave)

    fun getAllLeave(): LiveData<List<Leave>>

    fun getLeaveByUserId(id: Int): LiveData<List<LeaveAndType>>

    fun getAllLeaveAndType(): LiveData<List<LeaveAndType>>

    fun postApproves(approves: List<Approved>)

    fun postApprove(approve: Approved)
}
package com.vertice.teepop.leaveapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import android.util.Log
import com.vertice.teepop.leaveapp.data.LeaveRepository
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import com.vertice.teepop.leaveapp.data.remote.EmployeeApi
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveViewModel : ViewModel() {

    val TAG: String = LeaveViewModel::class.java.simpleName

    @Inject
    lateinit var leaveRepo: LeaveRepository

    private var types: LiveData<List<TypeLeave>>? = null
    private var leaveAndType: LiveData<PagedList<LeaveAndType>>? = null

    fun getAllType(): LiveData<List<TypeLeave>> {
        // This is a simple way to cache data. You could cache it in db instead.
        types = types ?: leaveRepo.getTypeLeave()
        return types!!
    }

    fun getLeaveAndType(): LiveData<PagedList<LeaveAndType>> {
        leaveAndType = leaveAndType ?: LivePagedListBuilder<Int, LeaveAndType>(leaveRepo.getAllLeaveAndType(), 20).build()
        return leaveAndType!!
    }

    fun getLeaveByUserId(id: Long): LiveData<PagedList<LeaveAndType>> {
        leaveAndType = leaveAndType ?: LivePagedListBuilder<Int, LeaveAndType>(leaveRepo.getLeaveByUserId(id), 20).build()
        return leaveAndType!!
    }

    fun postLeave(leave: Leave) {
        leaveRepo.postLeave(leave)
    }

    fun postApproved(approve: Approved) {
        leaveRepo.postApprove(approve)
    }

    fun reloadLeave(id: Long) {
        leaveAndType = LivePagedListBuilder<Int, LeaveAndType>(leaveRepo.getLeaveByUserId(id), 20).build()
    }

    fun reloadLeave() {
        leaveAndType = LivePagedListBuilder<Int, LeaveAndType>(leaveRepo.getAllLeaveAndType(), 20).build()
    }

    fun deleteLeave(id: Long) {
        leaveRepo.deleteLeave(id)
    }

}
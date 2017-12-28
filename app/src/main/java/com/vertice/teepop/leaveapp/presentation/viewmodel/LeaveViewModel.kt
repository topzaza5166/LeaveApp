package com.vertice.teepop.leaveapp.presentation.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.vertice.teepop.leaveapp.data.LeaveRepository
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import javax.inject.Inject

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveViewModel : ViewModel() {

    val TAG: String = LeaveViewModel::class.java.simpleName

    @Inject
    lateinit var leaveRepo: LeaveRepository

    private var leave: LiveData<List<Leave>>? = null

    private var types: LiveData<List<TypeLeave>>? = null

    private var leavaAndType: LiveData<List<LeaveAndType>>? = null

    fun getAllType(): LiveData<List<TypeLeave>> {
        // This is a simple way to cache data. You could cache it in db instead.
        types = types ?: leaveRepo.getTypeLeave()
        return types!!
    }

    fun getAllLeave(): LiveData<List<Leave>> {
        leave = leave ?: leaveRepo.getAllLeave()
        return leave!!
    }

    fun getLeaveAndType(): LiveData<List<LeaveAndType>> {
        leavaAndType = leavaAndType ?: leaveRepo.getAllLeaveAndType()
        return leavaAndType!!
    }

    fun getLeaveByUserId(id: Int): LiveData<List<LeaveAndType>> {
        leavaAndType = leavaAndType ?: leaveRepo.getLeaveByUserId(id)
        return leavaAndType!!
    }

    fun postLeave(leave: Leave) {
        leaveRepo.postLeave(leave)
    }

}
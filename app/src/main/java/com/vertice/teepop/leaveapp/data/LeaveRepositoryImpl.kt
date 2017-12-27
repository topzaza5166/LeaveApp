package com.vertice.teepop.leaveapp.data

import android.arch.lifecycle.LiveData
import android.util.Log
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.local.LeaveDao
import com.vertice.teepop.leaveapp.data.local.TypeLeaveDao
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import io.reactivex.Scheduler

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveRepositoryImpl(val serviceApi: LeaveApi,
                          val typeLeaveDao: TypeLeaveDao,
                          val leaveDao: LeaveDao,
                          val scheduler: Scheduler) : LeaveRepository {

    val TAG: String = LeaveRepositoryImpl::class.java.simpleName

    override fun getAllLeave(): LiveData<List<Leave>> {
        serviceApi.getAllLeave()
                .subscribeOn(scheduler)
                .subscribe({ leaves, _ ->
                    leaves?.let { leaveDao.insertOrUpdateLeave(*leaves.toTypedArray()) }
                })
        return leaveDao.getAllLeave()
    }

    override fun getLeaveByUserId(id: Int): LiveData<List<Leave>> {
        serviceApi.getLeaveByUserId(id)
                .subscribeOn(scheduler)
                .subscribe({ leaves, _ ->
                    leaves?.let { leaveDao.insertOrUpdateLeave(*leaves.toTypedArray()) }
                })
        return leaveDao.getLeaveByUserId(id)
    }

    override fun postLeave(leave: Leave) {
        serviceApi.postLeave(leave)
                .subscribeOn(scheduler)
                .subscribe({ result, _ ->
                    result?.let { leaveDao.insertOrUpdateLeave(result) }
                })
    }

    override fun getTypeLeave(): LiveData<List<TypeLeave>> {
        serviceApi.getType()
                .subscribeOn(scheduler)
                .subscribe { type, _ ->
                    type?.let { typeLeaveDao.insertOrUpdateType(*type.toTypedArray()) }
                }

        return typeLeaveDao.getAllType()
    }


}
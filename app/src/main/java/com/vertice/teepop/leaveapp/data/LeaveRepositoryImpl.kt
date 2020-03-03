package com.vertice.teepop.leaveapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import android.util.Log
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.local.LeaveDao
import com.vertice.teepop.leaveapp.data.local.TypeLeaveDao
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import io.reactivex.Scheduler

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveRepositoryImpl(val serviceApi: LeaveApi,
                          val typeLeaveDao: TypeLeaveDao,
                          val leaveDao: LeaveDao,
                          val scheduler: Scheduler) : LeaveRepository {

    override fun deleteLeave(id: Long) {
        serviceApi.deleteLeave(id)
                .subscribeOn(scheduler)
                .subscribe({ leave, _ ->
                    leave.let { leaveDao.deleteLeave(it) }
                })
    }

    val TAG: String = LeaveRepositoryImpl::class.java.simpleName

    override fun getAllLeaveAndType(): DataSource.Factory<Int, LeaveAndType> {
        serviceApi.getAllLeave()
                .subscribeOn(scheduler)
                .subscribe({ leaves, _ ->
                    leaves?.let { leaveDao.insertOrUpdateLeave(*leaves.toTypedArray()) }
                })
        return leaveDao.getLeaveAndType()
    }

    override fun getAllLeave(): LiveData<List<Leave>> {
        serviceApi.getAllLeave()
                .subscribeOn(scheduler)
                .subscribe({ leaves, _ ->
                    leaves?.let { leaveDao.insertOrUpdateLeave(*leaves.toTypedArray()) }
                })
        return leaveDao.getAllLeave()
    }

    override fun getLeaveByUserId(id: Long): DataSource.Factory<Int, LeaveAndType> {
        Log.i(TAG, "id = $id")
        serviceApi.getLeaveByUserId(id)
                .subscribeOn(scheduler)
                .subscribe(
                        { leaves -> leaves?.let { leaveDao.insertOrUpdateLeave(*leaves.toTypedArray()) } },
                        { error -> Log.e(TAG, "$error") })

        return leaveDao.getLeaveByUserId(id)
    }

    override fun postLeave(leave: Leave) {
        serviceApi.postLeave(leave)
                .subscribeOn(scheduler)
                .subscribe(
                        { response -> response?.let { leaveDao.insertOrUpdateLeave(response) } },
                        { error -> Log.e(TAG, error?.toString()) }
                )
    }

    override fun getTypeLeave(): LiveData<List<TypeLeave>> {
        serviceApi.getType()
                .subscribeOn(scheduler)
                .subscribe(
                        { type -> type?.let { typeLeaveDao.insertOrUpdateType(*type.toTypedArray()) } },
                        { error -> Log.e(TAG, "$error") }
                )

        return typeLeaveDao.getAllType()
    }

    override fun postApproves(approves: List<Approved>) {
        serviceApi.postApproves(approves)
                .subscribeOn(scheduler)
                .subscribe(
                        { response -> response?.let { leaveDao.insertOrUpdateLeave(*response.toTypedArray()) } },
                        { error -> Log.e(TAG, error?.toString()) }
                )
    }

    override fun postApprove(approve: Approved) {
        serviceApi.postApprove(approve)
                .subscribeOn(scheduler)
                .subscribe(
                        { response -> response?.let { leaveDao.insertOrUpdateLeave(response) } },
                        { error -> Log.e(TAG, error?.toString()) }
                )
    }

}
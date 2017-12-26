package com.vertice.teepop.leaveapp.data

import android.arch.lifecycle.LiveData
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.local.TypeLeaveDao
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import io.reactivex.Scheduler

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveRepositoryImpl(val serviceApi: LeaveApi, val dao: TypeLeaveDao, val scheduler: Scheduler) : LeaveRepository {

    override fun getTypeLeave(): LiveData<List<TypeLeave>> {
        serviceApi.getType()
                .subscribeOn(scheduler)
                .subscribe { type, _ ->
                    type?.let { dao.insertOrUpdateType(*type.toTypedArray()) }
                }

        return dao.getAllType()
    }

}
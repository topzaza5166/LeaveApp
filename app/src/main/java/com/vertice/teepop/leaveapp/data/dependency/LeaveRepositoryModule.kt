package com.vertice.teepop.leaveapp.data.dependency

import com.vertice.teepop.leaveapp.data.LeaveRepository
import com.vertice.teepop.leaveapp.data.LeaveRepositoryImpl
import com.vertice.teepop.leaveapp.data.local.TypeLeaveDao
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/26/2017.
 */
@Module
class LeaveRepositoryModule {

    @Provides
    @Singleton
    fun provideLeaveRepository(typeLeaveDao: TypeLeaveDao, leaveApi: LeaveApi): LeaveRepository
            = LeaveRepositoryImpl(leaveApi, typeLeaveDao, Schedulers.io())
}
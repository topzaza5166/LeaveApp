package com.vertice.teepop.leaveapp.presentation.dependency

import com.vertice.teepop.leaveapp.data.dependency.LeaveRepositoryModule
import com.vertice.teepop.leaveapp.data.dependency.LocalDataModule
import com.vertice.teepop.leaveapp.data.dependency.RemoteDataModule
import com.vertice.teepop.leaveapp.presentation.leave.LeaveActivity
import com.vertice.teepop.leaveapp.presentation.leave.LeaveViewModel
import com.vertice.teepop.leaveapp.presentation.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/26/2017.
 */
@Singleton
@Component(
        modules = arrayOf(
                RemoteDataModule::class,
                LocalDataModule::class,

                LeaveRepositoryModule::class
        )
)
interface AppComponent {

    fun inject(loginActivity: LoginActivity)
//    fun inject(leaveActivity: LeaveActivity)
    fun inject(leaveViewModel: LeaveViewModel)
}
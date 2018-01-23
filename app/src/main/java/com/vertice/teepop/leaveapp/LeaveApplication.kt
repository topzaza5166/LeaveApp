package com.vertice.teepop.leaveapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.vertice.teepop.leaveapp.data.dependency.LeaveRepositoryModule
import com.vertice.teepop.leaveapp.data.dependency.LocalDataModule
import com.vertice.teepop.leaveapp.data.dependency.RemoteDataModule
import com.vertice.teepop.leaveapp.presentation.dependency.AppComponent
import com.vertice.teepop.leaveapp.presentation.dependency.DaggerAppComponent

/**
 * Created by VerDev06 on 12/25/2017.
 */
class LeaveApplication : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    private val BASE_URL = "http://192.168.1.75:6789/"

    override fun onCreate() {
        super.onCreate()

        Hawk.init(applicationContext).build()

        component = DaggerAppComponent.builder()
                .remoteDataModule(RemoteDataModule(BASE_URL))
                .localDataModule(LocalDataModule(applicationContext))
                .leaveRepositoryModule(LeaveRepositoryModule())
                .build()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
package com.vertice.teepop.leaveapp.data.dependency

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import com.vertice.teepop.leaveapp.data.remote.LoginApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/26/2017.
 */
@Module
class RemoteDataModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat("MMM d, yyy HH:mm:ss a")
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .build()

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi =
            retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideLeaveApi(retrofit: Retrofit): LeaveApi =
            retrofit.create(LeaveApi::class.java)

}
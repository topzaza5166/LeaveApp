package com.vertice.teepop.leaveapp.data.dependency

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vertice.teepop.leaveapp.data.remote.EmployeeApi
import com.vertice.teepop.leaveapp.data.remote.LeaveApi
import com.vertice.teepop.leaveapp.data.remote.LoginApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .client(client)
                    .build()

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi =
            retrofit.create(LoginApi::class.java)
    @Provides
    @Singleton
    fun provideLeaveApi(retrofit: Retrofit): LeaveApi =
            retrofit.create(LeaveApi::class.java)
    @Provides
    @Singleton
    fun provideEmployeeApi(retrofit: Retrofit): EmployeeApi =
            retrofit.create(EmployeeApi::class.java)

}
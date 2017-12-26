package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.model.Employee
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by VerDev06 on 12/26/2017.
 */
interface LoginApi {

    data class LoginBody(var userName: String, var password: String)

    @POST("auth/login")
    fun login(@Body body: LoginBody): Single<Employee>
}


package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.model.Employee
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by VerDev06 on 1/10/2018.
 */
interface EmployeeApi {

    @GET("employee/{id}")
    fun getEmployeeById(@Path("id") id: Long): Single<Employee>
}

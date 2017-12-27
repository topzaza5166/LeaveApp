package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by VerDev06 on 12/26/2017.
 */
interface LeaveApi {

    @GET("typeLeave")
    fun getType(): Single<List<TypeLeave>>

    @POST("leave")
    fun postLeave(leave: Leave): Single<Leave>

    @GET("leave")
    fun getAllLeave(): Single<List<Leave>>

    @GET("user/{id}")
    fun getLeaveByUserId(@Path("id") id: Int): Single<List<Leave>>
}
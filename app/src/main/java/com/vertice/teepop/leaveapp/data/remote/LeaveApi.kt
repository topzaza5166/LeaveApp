package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Approved

import io.reactivex.Single
import okhttp3.Response

import retrofit2.http.*

/**
 * Created by VerDev06 on 12/26/2017.
 */
interface LeaveApi {

    @GET("typeLeave")
    fun getType(): Single<List<TypeLeave>>

    @POST("leave")
    fun postLeave(@Body leave: Leave): Single<Leave>

    @GET("leave")
    fun getAllLeave(): Single<List<Leave>>

    @GET("leave/user/{id}")
    fun getLeaveByUserId(@Path("id") id: Long): Single<List<Leave>>

    @POST("leave/approves")
    fun postApproves(@Body approves: List<Approved>): Single<List<Leave>>

    @POST("leave/approve")
    fun postApprove(@Body approves: Approved): Single<Leave>

    @DELETE("leave/{id}")
    fun deleteLeave(@Path("id") id:Long): Single<Leave>

}

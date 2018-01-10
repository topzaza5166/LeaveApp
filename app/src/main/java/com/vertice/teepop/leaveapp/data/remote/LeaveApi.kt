package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Approved
import com.vertice.teepop.leaveapp.data.model.Employee
import io.reactivex.Single
import retrofit2.http.Body
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
    fun postLeave(@Body leave: Leave): Single<Leave>

    @GET("leave")
    fun getAllLeave(): Single<List<Leave>>

    @GET("leave/user/{id}")
    fun getLeaveByUserId(@Path("id") id: Int): Single<List<Leave>>

    @POST("leave/approves")
    fun postApproves(@Body approves: List<Approved>): Single<List<Leave>>

    @POST("leave/approve")
    fun postApprove(@Body approves: Approved): Single<Leave>
}

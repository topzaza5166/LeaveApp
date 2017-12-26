package com.vertice.teepop.leaveapp.data.remote

import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by VerDev06 on 12/26/2017.
 */
interface LeaveApi {

    @GET("typeLeave")
    fun getType(): Single<List<TypeLeave>>
}
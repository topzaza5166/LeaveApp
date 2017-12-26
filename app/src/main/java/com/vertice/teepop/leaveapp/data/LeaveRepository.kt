package com.vertice.teepop.leaveapp.data

import android.arch.lifecycle.LiveData
import com.vertice.teepop.leaveapp.data.entity.TypeLeave


/**
 * Created by VerDev06 on 12/25/2017.
 */
interface LeaveRepository {

    fun getTypeLeave(): LiveData<List<TypeLeave>>

}
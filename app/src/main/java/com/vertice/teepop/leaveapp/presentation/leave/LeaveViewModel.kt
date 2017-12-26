package com.vertice.teepop.leaveapp.presentation.leave

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.vertice.teepop.leaveapp.data.LeaveRepository
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import javax.inject.Inject

/**
 * Created by VerDev06 on 12/26/2017.
 */
class LeaveViewModel : ViewModel() {

    @Inject
    lateinit var leaveRepo: LeaveRepository

    private var types: LiveData<List<TypeLeave>>? = null

    fun getAllType(): LiveData<List<TypeLeave>> {

        // This is a simple way to cache data. You could cache it in db instead.
        types = types ?: leaveRepo.getTypeLeave()
        return types!!
    }
}
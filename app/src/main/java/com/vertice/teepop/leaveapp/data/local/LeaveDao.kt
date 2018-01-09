package com.vertice.teepop.leaveapp.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.model.LeaveAndType

/**
 * Created by VerDev06 on 12/27/2017.
 */
@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateLeave(vararg leave: Leave)

    @Query("SELECT * FROM Leave ORDER BY id DESC")
    fun getAllLeave(): LiveData<List<Leave>>

    @Transaction
    @Query("SELECT * FROM Leave WHERE userId LIKE :userId ORDER BY id DESC")
    fun getLeaveByUserId(userId: Int): LiveData<List<LeaveAndType>>

    @Transaction
    @Query("SELECT * FROM Leave ORDER BY id DESC")
    fun getLeaveAndType(): LiveData<List<LeaveAndType>>
}
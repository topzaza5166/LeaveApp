package com.vertice.teepop.leaveapp.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.model.LeaveAndType

/**
 * Created by VerDev06 on 12/27/2017.
 */
@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateLeave(vararg leave: Leave)

    @Query("SELECT * FROM Leave")
    fun getAllLeave(): LiveData<List<Leave>>

    @Query("SELECT * FROM Leave WHERE userId LIKE :arg0")
    fun getLeaveByUserId(userId: Int): LiveData<List<Leave>>

    @Query("SELECT * FROM Leave" +
            "INNER JOIN Type_Leave ON Leave.typeId = Type_Leave.id")
    fun getLeaveAndType(): LiveData<List<LeaveAndType>>
}
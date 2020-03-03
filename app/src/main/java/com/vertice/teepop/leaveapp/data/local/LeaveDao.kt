package com.vertice.teepop.leaveapp.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.model.LeaveAndType

/**
 * Created by VerDev06 on 12/27/2017.
 */
@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateLeave(vararg leave: Leave)

    @Delete
    fun deleteLeave(leave: Leave)

    @Query("SELECT * FROM Leave ORDER BY id DESC")
    fun getAllLeave(): LiveData<List<Leave>>

    @Transaction
    @Query("SELECT * FROM Leave WHERE userId LIKE :userId ORDER BY id DESC")
    fun getLeaveByUserId(userId: Long): DataSource.Factory<Int, LeaveAndType>

    @Transaction
    @Query("SELECT * FROM Leave ORDER BY id DESC")
    fun getLeaveAndType(): DataSource.Factory<Int, LeaveAndType>
}
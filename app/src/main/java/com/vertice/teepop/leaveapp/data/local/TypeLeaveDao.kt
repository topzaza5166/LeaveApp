package com.vertice.teepop.leaveapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vertice.teepop.leaveapp.data.entity.TypeLeave

/**
 * Created by VerDev06 on 12/26/2017.
 */
@Dao
interface TypeLeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateType(vararg type: TypeLeave)

    @Query("SELECT * FROM Type_Leave")
    fun getAllType(): LiveData<List<TypeLeave>>

}
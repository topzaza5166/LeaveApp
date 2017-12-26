package com.vertice.teepop.leaveapp.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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
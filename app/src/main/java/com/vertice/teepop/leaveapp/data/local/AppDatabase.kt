package com.vertice.teepop.leaveapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vertice.teepop.leaveapp.data.entity.TypeLeave

/**
 * Created by VerDev06 on 12/26/2017.
 */

@Database(entities = arrayOf(TypeLeave::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun typeLeaveDao(): TypeLeaveDao
}
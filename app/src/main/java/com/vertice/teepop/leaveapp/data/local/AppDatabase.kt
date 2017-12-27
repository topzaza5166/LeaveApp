package com.vertice.teepop.leaveapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave

/**
 * Created by VerDev06 on 12/26/2017.
 */

@Database(entities = arrayOf(TypeLeave::class, Leave::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun typeLeaveDao(): TypeLeaveDao

    abstract fun leaveDao(): LeaveDao
}
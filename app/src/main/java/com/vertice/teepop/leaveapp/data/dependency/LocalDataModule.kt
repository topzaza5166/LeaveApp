package com.vertice.teepop.leaveapp.data.dependency

import androidx.room.Room
import android.content.Context
import com.vertice.teepop.leaveapp.data.local.AppDatabase
import com.vertice.teepop.leaveapp.data.local.LeaveDao
import com.vertice.teepop.leaveapp.data.local.TypeLeaveDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by VerDev06 on 12/26/2017.
 */

@Module
class LocalDataModule(val context: Context) {

    private val DB_FILE_NAME = "LEAVE_DB"

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_FILE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    fun provideTypeLeaveDao(db: AppDatabase): TypeLeaveDao =
            db.typeLeaveDao()

    @Provides
    @Singleton
    fun provideLeaveDao(db: AppDatabase): LeaveDao =
            db.leaveDao()


}
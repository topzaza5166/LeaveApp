package com.vertice.teepop.leaveapp.data.model

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.entity.converter.DateConverter
import java.util.*

/**
 * Created by topza on 12/27/2017.
 */
class LeaveAndType {

    @Embedded
    lateinit var leave: Leave

    @Relation(parentColumn = "typeId", entityColumn = "id", entity = TypeLeave::class)
    lateinit var type: List<TypeLeave>

}
package com.vertice.teepop.leaveapp.data.model

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by VerDev06 on 12/29/2017.
 */
data class Approved(var id: Long = 0,
                    var leaveId: Long = 0,
                    var adminId: Long = 0,
                    var comment: String? = null,
                    var createdDate: Date = Date()) {

}
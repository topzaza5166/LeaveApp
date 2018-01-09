package com.vertice.teepop.leaveapp.data.model

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by VerDev06 on 12/29/2017.
 */
data class Approved(var id: Int = 0,
                    var leaveId: Int = 0,
                    var adminId: Int = 0,
                    var comment: String? = null,
                    var createdDate: Date = Date()) {

}
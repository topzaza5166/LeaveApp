package com.vertice.teepop.leaveapp.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by VerDev06 on 12/25/2017.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Employee(var name: String = "",
                    var userName: String = "",
                    var roleId: Int = 0,
                    var startWorkDate: Date? = Date(),
                    var status: Int = 0,
                    var createdDate: Date? = Date()) : Parcelable {

}
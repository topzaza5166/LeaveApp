package com.vertice.teepop.leaveapp.presentation

import android.annotation.SuppressLint
import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by VerDev06 on 1/9/2018.
 */
object DateConverter {

    @SuppressLint("SimpleDateFormat")
    @BindingConversion
    @JvmStatic
    fun convertDateToDisplayTime(date: Date?): String {
        date?.let {
            return SimpleDateFormat("yyyy/MM/dd").format(it)
        }
        return ""
    }
}
package com.vertice.teepop.leaveapp.util.template;

import android.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VerDev06 on 1/4/2018.
 */

public class DateConverter {

    @BindingConversion
    public static String convertDateToDisplayTime(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }
}

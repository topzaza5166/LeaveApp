package com.vertice.teepop.leaveapp.presentation.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.view.state.BundleSavedState
import kotlinx.android.synthetic.main.view_date_form.view.*
import java.util.*


/**
 * Created by nuuneoi on 11/16/2014.
 */
class DateFromView : BaseCustomViewGroup {

    val TAG: String = this::class.java.simpleName

    val KEY_YEAR: String = "year"
    val KEY_MONTH: String = "month"
    val KEY_DAY: String = "day"

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0

    constructor(context: Context) : super(context) {
        initInflate()
        initInstances()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initInflate() {
        inflate(context, R.layout.view_date_form, this)
    }

    private fun initInstances() {
        // findViewById hereval c = Calendar.getInstance()
        val c = Calendar.getInstance()
        c.let {
            val year = it.get(Calendar.YEAR)
            val month = it.get(Calendar.MONTH)
            val day = it.get(Calendar.DAY_OF_MONTH)

            setDate(year, month, day)
        }
    }

    private fun initWithAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        val savedState = BundleSavedState(superState).apply {
            bundle.putInt(KEY_YEAR, year)
            bundle.putInt(KEY_MONTH, month)
            bundle.putInt(KEY_DAY, day)
        }

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as BundleSavedState
        super.onRestoreInstanceState(ss.superState)

        val bundle = ss.bundle
        bundle.let {
            year = it.getInt(KEY_YEAR)
            month = it.getInt(KEY_MONTH)
            day = it.getInt(KEY_DAY)
        }
    }

    fun setTextToDate() {
        textFromTo.text = resources.getText(R.string.to)
        viewMargin.visibility = View.VISIBLE
    }

    fun setTextFromDate() {
        textFromTo.text = resources.getText(R.string.from)
    }

    @SuppressLint("SetTextI18n")
    fun setDate(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day

        textDate.text = "${this.day}"
        textMonth.text = "${this.month + 1}"
        textYear.text = "${this.year}"
    }

    fun getDate(): Date {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        return date.time
    }

}

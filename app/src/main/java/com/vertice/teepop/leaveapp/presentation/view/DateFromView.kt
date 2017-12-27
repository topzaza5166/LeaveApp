package com.vertice.teepop.leaveapp.presentation.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.view.state.BundleSavedState
import kotlinx.android.synthetic.main.view_date_form.view.*
import java.util.*


/**
 * Created by nuuneoi on 11/16/2014.
 */
class DateFromView : BaseCustomViewGroup {

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
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        setDate(year, month, day)
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

        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return BundleSavedState(superState)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as BundleSavedState
        super.onRestoreInstanceState(ss.superState)

        val bundle = ss.bundle
        // Restore State from bundle here
    }

    fun setTextFormTo(text: String) {
        textFromTo.text = text
    }

    fun setDate(year: Int, month: Int, day: Int) {
        textDate.text = day.toString()
        textMonth.text = month.toString()
        textYear.text = year.toString()
    }

}

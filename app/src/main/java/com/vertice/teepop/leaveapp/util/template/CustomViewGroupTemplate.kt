package com.vertice.teepop.leaveapp.util.template

import android.annotation.TargetApi
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.view.BaseCustomViewGroup
import com.vertice.teepop.leaveapp.presentation.view.state.BundleSavedState


/**
 * Created by nuuneoi on 11/16/2014.
 */
class CustomViewGroupTemplate : BaseCustomViewGroup {

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
        inflate(context, R.layout.activity_main, this)
    }

    private fun initInstances() {
        // findViewById here
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

}

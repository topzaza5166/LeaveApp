package com.vertice.teepop.leaveapp.presentation.leave

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import com.orhanobut.hawk.Hawk
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.Employee

import com.vertice.teepop.leaveapp.util.Constant

import kotlinx.android.synthetic.main.activity_leave.*
import kotlinx.android.synthetic.main.content_leave.*
import java.util.*


class LeaveActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {


    private val TAG: String = LeaveActivity::class.java.simpleName

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LeaveViewModel::class.java).also {
            LeaveApplication.component.inject(it)
        }
    }

    val employee = Hawk.get<Employee>(Constant.USER_KEY, Employee())
    var timeLate: Int = 0

    var typeList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave)
        setSupportActionBar(toolbar)

        Log.i(TAG, employee.toString())
        supportActionBar?.title = employee.name

        setSpinner()
        setDatePicker()
        setTimePicker()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Your Type Selected is ${typeList?.get(spinnerType.selectedItemPosition)}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun setTimePicker() {
        textTimeLate.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val dialog = TimePickerDialog(this, this, hour - 9, minute, true)
            dialog.show()
        }
    }

    private fun setDatePicker() {
        fromView.setTextFormTo(resources.getString(R.string.from))
        fromView.setOnClickListener {

        }
        toView.setTextFormTo(resources.getString(R.string.to))
        toView.setOnClickListener {

        }
    }


    private fun setSpinner() {
        spinnerType.onItemSelectedListener = onItemSelected
        viewModel.getAllType().observe(this, Observer {
            it?.let {
                typeList = it.map { typeLeave -> typeLeave.typeName }
                spinnerType.adapter = ArrayAdapter<String>(this@LeaveActivity,
                        android.R.layout.simple_spinner_dropdown_item, typeList)

                Log.i(TAG, "TypeLeave is $typeList")
            }
        })
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        val time = "Time Late : $hour Hour $minute Minute"
        textTimeLate.text = time

        timeLate = hour * 60 + minute
        Log.d(TAG, timeLate.toString())
    }


    private val onItemSelected: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (typeList?.get(spinnerType.selectedItemPosition).equals("Late arrival")) {
                toggleView()
            } else {

            }
        }
    }

    private fun toggleView() {
        //TODO: Toggle Visible and Gone
        if (toView.visibility == View.GONE)
            toView.visibility = View.VISIBLE
        else toView.visibility = View.GONE

        fromView.visibility = View.GONE
        textTimeLate.visibility = View.VISIBLE
    }
}

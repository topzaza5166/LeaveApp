package com.vertice.teepop.leaveapp.presentation.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.presentation.viewmodel.LeaveViewModel
import com.vertice.teepop.leaveapp.util.Constant.KEY_ARG_EMPLOYEE
import kotlinx.android.synthetic.main.fragment_leave_form.*
import kotlinx.android.synthetic.main.view_date_form.view.*
import java.util.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveFormFragment : Fragment(), TimePickerDialog.OnTimeSetListener {

    private val TAG: String = this::class.java.simpleName

    lateinit var employee: Employee

    var timeLate: Int = 0
    var typeList: List<TypeLeave>? = null

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(LeaveViewModel::class.java).also {
            LeaveApplication.component.inject(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_leave_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances(view, savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
        employee = arguments?.getParcelable(KEY_ARG_EMPLOYEE) ?: Employee()
        Log.i(TAG, "$employee")
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        setSpinner()
        setDatePicker()
        setTimePicker()

        fab.setOnClickListener {
            sendLeave()
            editReason.setText("")

            Toast.makeText(context, "Send Your Leave ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    /*
     * Save Instance State Here
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance State here
    }

    private fun setSpinner() {
        spinnerType.onItemSelectedListener = onItemSelected
        viewModel.getAllType().observe(this, Observer {
            it?.let {
                typeList = it
                spinnerType.adapter = ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item,
                        typeList?.map { typeLeave -> typeLeave.typeName }
                )

                Log.i(TAG, "TypeLeave is $typeList")
            }
        })

    }

    private fun setDatePicker() {
        fromView.setTextFromDate()
        fromView.clickChange.setOnClickListener {
            val dialog = getDatePickerDialog({ _, year, month, day ->
                fromView.setDate(year, month, day)
            })
            dialog.show()
        }

        toView.setTextToDate()
        toView.clickChange.setOnClickListener {
            val dialog = getDatePickerDialog({ _, year, month, day ->
                toView.setDate(year, month, day)
            })
            dialog.show()
        }
    }

    private fun setTimePicker() {
        buttonTimeLate.setOnClickListener {
            Calendar.getInstance().also {
                val hour = it.get(Calendar.HOUR_OF_DAY)
                val minute = it.get(Calendar.MINUTE)

                val dialog = TimePickerDialog(context, this, hour - 9, minute, true)
                dialog.show()
            }
        }
    }

    private fun getDatePickerDialog(listener: (DatePicker, Int, Int, Int) -> Unit): DatePickerDialog {
        Calendar.getInstance().apply {
            val year = get(Calendar.YEAR)
            val month = get(Calendar.MONTH)
            val day = get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(context, listener, year, month, day)
        }

        return DatePickerDialog(context, listener, 0, 0, 0)
    }

    private fun sendLeave() {
        val leave = Leave().apply {
            userId = employee.id
            typeId = typeList?.get(spinnerType.selectedItemPosition)?.id ?: 0
            leaveDate = Date()
            fromDate = fromView.getDate()
            toDate = toView.getDate()
            timeLate = this@LeaveFormFragment.timeLate
            reason = editReason.text.toString()
        }
        Log.i(TAG, leave.toString())
        Log.i(TAG, Gson().toJson(leave))
        viewModel.postLeave(leave)
    }

    private fun toggleView() {
        toView.apply {
            visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
        }
        fromView.apply {
            visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
        }

        layoutTimeLate.apply {
            visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
        }
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
            if (typeList?.get(spinnerType.selectedItemPosition)?.typeName.equals("Late arrival")
                    && layoutTimeLate.visibility == View.GONE) {
                toggleView()
            } else if (layoutTimeLate.visibility == View.VISIBLE) {
                toggleView()
            }
        }
    }

    companion object {

        fun newInstance(employee: Employee): LeaveFormFragment {
            val fragment = LeaveFormFragment()
            val args = Bundle()
            args.putParcelable(KEY_ARG_EMPLOYEE, employee)
            fragment.arguments = args
            return fragment
        }
    }

}

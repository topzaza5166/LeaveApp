package com.vertice.teepop.leaveapp.presentation.leave

import android.arch.lifecycle.Observer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.util.Constant.KEY_ARG_EMPLOYEE
import kotlinx.android.synthetic.main.fragment_leave_form.*
import java.util.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveFormFragment : Fragment(), TimePickerDialog.OnTimeSetListener {

    private val TAG: String = LeaveActivity::class.java.simpleName

    lateinit var employee: Employee

    var timeLate: Int = 0
    var typeList: List<TypeLeave>? = null

    private val viewModel by lazy {
        ViewModelProviders.of(activity!!).get(LeaveViewModel::class.java)
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
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        setSpinner()
        setDatePicker()
        setTimePicker()
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
        fromView.setTextFormTo(resources.getString(R.string.from))
        fromView.setOnClickListener {
            val dialog = getDatePickerDialog({ _, year, month, day ->
                fromView.setDate(year, month + 1, day)
            })
            dialog.show()
        }
        toView.setTextFormTo(resources.getString(R.string.to))
        toView.setOnClickListener {
            val dialog = getDatePickerDialog({ _, year, month, day ->
                toView.setDate(year, month + 1, day)
            })
            dialog.show()
        }
    }

    private fun setTimePicker() {
        textTimeLate.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val dialog = TimePickerDialog(context, this, hour - 9, minute, true)
            dialog.show()
        }
    }

    private fun getDatePickerDialog(listener: (DatePicker, Int, Int, Int) -> Unit): DatePickerDialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context, listener, year, month, day)
    }

    fun sendLeave() {
        val leave = Leave()
        leave.apply {
            userId = employee.id
            typeId = typeList?.get(spinnerType.selectedItemPosition)?.id ?: 0
            leaveDate = Date()
            fromDate = fromView.getDate()
            toDate = toView.getDate()
            timeLate = this@LeaveFormFragment.timeLate
            reason = editReason.text.toString()
        }
        viewModel.postLeave(leave)
    }

    private fun toggleView() {
        toView.apply {
            visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
        }
        fromView.apply {
            visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
        }

        textTimeLate.apply {
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
                    && textTimeLate.visibility == View.GONE) {
                toggleView()
            } else if (textTimeLate.visibility == View.VISIBLE) {
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

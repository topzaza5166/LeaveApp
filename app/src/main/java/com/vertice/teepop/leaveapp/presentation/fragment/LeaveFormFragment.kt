package com.vertice.teepop.leaveapp.presentation.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.presentation.viewmodel.LeaveViewModel
import com.vertice.teepop.leaveapp.util.Constant.KEY_ARG_EMPLOYEE
import com.vertice.teepop.leaveapp.util.NotificationUtil
import com.vertice.teepop.leaveapp.util.bus.SendMessageEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_leave_form.*
import kotlinx.android.synthetic.main.view_date_form.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
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
        Log.i(TAG, "$employee")
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        setSpinner()
        setDatePicker()
        setTimePicker()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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
                spinnerType.adapter = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_dropdown_item,
                        typeList?.map { typeLeave -> typeLeave.typeName }!!.toMutableList()
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
            Calendar.getInstance().let {
                TimePickerDialog(context, this, it.get(Calendar.HOUR_OF_DAY) - 9, it.get(Calendar.MINUTE), true).show()
            }
        }
    }

    private fun getDatePickerDialog(listener: (DatePicker, Int, Int, Int) -> Unit): DatePickerDialog {
        return Calendar.getInstance().run {
            DatePickerDialog(context!!, listener, get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun sendLeave() {
        if (typeList?.get(spinnerType.selectedItemPosition)?.typeName.equals("Early leave")) {
            Toast.makeText(context, "Early Morning ${radioMorning.isChecked} \nEarly Afternoon ${radioAfternoon.isChecked}", Toast.LENGTH_SHORT).show()
        }

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
        viewModel.postLeave(leave)

        Observable.fromCallable { NotificationUtil.pushNotification("admin", "Leave", leave.reason, null) }
                .subscribeOn(Schedulers.io())
                .subscribe({ t -> Log.i(TAG, t) },
                        { t -> Log.i(TAG, "Error ${t.message}") })
    }

    private fun setToFromVisibility(vis: Int) {
        toView.apply {
            visibility = vis
        }
        fromView.apply {
            visibility = vis
        }

    }

    private fun setTimeLateEarlyGroupVisibility(mode: String) {
        when (mode) {
            "Late arrival" -> {
                radioEarlyGroup.visibility = View.GONE
                layoutTimeLate.visibility = View.VISIBLE
            }
            "Early leave" -> {
                radioEarlyGroup.visibility = View.VISIBLE
                layoutTimeLate.visibility = View.GONE
            }
            else -> {
                radioEarlyGroup.visibility = View.GONE
                layoutTimeLate.visibility = View.GONE
            }

        }
    }

    @Subscribe
    fun onClickFloatingButton(massage: SendMessageEvent) {
        sendLeave()
        editReason.setText("")

        Toast.makeText(context, "Send Your Leave ", Toast.LENGTH_SHORT).show()
    }

    /**
     * listener
     */
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
                setToFromVisibility(View.GONE)
                setTimeLateEarlyGroupVisibility("Late arrival")

            } else if (typeList?.get(spinnerType.selectedItemPosition)?.typeName.equals("Early leave")
                    && radioEarlyGroup.visibility == View.GONE) {
                setToFromVisibility(View.GONE)
                setTimeLateEarlyGroupVisibility("Early leave")

            } else if (layoutTimeLate.visibility == View.VISIBLE || radioEarlyGroup.visibility == View.VISIBLE) {
                setToFromVisibility(View.VISIBLE)
                setTimeLateEarlyGroupVisibility("")
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

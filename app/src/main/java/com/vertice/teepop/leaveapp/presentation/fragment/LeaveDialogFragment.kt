package com.vertice.teepop.leaveapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatCheckBox
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import com.vertice.teepop.leaveapp.data.remote.EmployeeApi
import com.vertice.teepop.leaveapp.data.remote.LoginApi
import com.vertice.teepop.leaveapp.databinding.DialogFragmentLeaveBinding
import com.vertice.teepop.leaveapp.databinding.ContentCardViewBinding
import com.vertice.teepop.leaveapp.util.bus.ApproveMessageEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveDialogFragment : DialogFragment() {

    @Inject
    lateinit var employeeApi: EmployeeApi

    val TAG: String = this::class.java.simpleName

    lateinit var binding: DialogFragmentLeaveBinding

    val ARG_KEY_LEAVE: String = "Leave"
    val ARG_KEY_MODE: String = "Mode"
    val ARG_KEY_ADMIN_ID: String = "AdminId"

    var mLeave: LeaveAndType = LeaveAndType()
    var mMode: String = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeaveApplication.component.inject(this)

        init(savedInstanceState)

        savedInstanceState?.let {
            onRestoreInstanceState(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DialogFragmentLeaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances(view, savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
        arguments?.apply {
            mLeave = getParcelable(ARG_KEY_LEAVE) ?: LeaveAndType()
            mMode = getString(ARG_KEY_MODE, "")
        }
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        binding.includeContentCardView?.apply {
            item = mLeave
            mode = mMode
            if (mMode == "admin") {
                enableApprove()
            }
        }

        mLeave.leave.approves.getOrNull(0)?.let {
            binding.approved1 = it
            employeeApi.getEmployeeById(it.adminId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { employee, _ ->
                        employee?.apply {
                            binding.textApprovedAdmin.text = name
                        }
                    }
        }

        mLeave.leave.approves.getOrNull(1)?.let {
            binding.approved2 = it
            employeeApi.getEmployeeById(it.adminId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { employee, _ ->
                        employee?.apply {
                            binding.textApprovedAdmin2.text = name
                        }
                    }
        }
    }

    private fun ContentCardViewBinding.enableApprove() {
        checkApprovedCardView1.apply {
            isEnabled = mLeave.leave.approves.size < 2
            setOnClickListener {
                showInputDialog(this)
            }
        }
        checkApprovedCardView2.apply {
            isEnabled = mLeave.leave.approves.isEmpty()
            setOnClickListener {
                showInputDialog(this)
            }
        }

    }

    private fun AppCompatCheckBox.showInputDialog(appCompatCheckBox: AppCompatCheckBox) {
        val viewInflated: View = LayoutInflater.from(context).inflate(R.layout.dialog_input, view as ViewGroup, false)
        val input: EditText = viewInflated.findViewById(R.id.input)
        AlertDialog.Builder(activity!!)
                .setView(viewInflated)
                .setMessage("Please input comment")
                .setPositiveButton("Approve", { _, _ ->
                    EventBus.getDefault().post(ApproveMessageEvent(
                            leaveId = mLeave.leave.id, comment = input.text.toString()
                    ))
                    Log.i(TAG, input.text.toString())
                    this@LeaveDialogFragment.dismiss()
                })
                .setNegativeButton("Cancel", { dialog, _ ->
                    dialog.cancel()
                })
                .setOnCancelListener { appCompatCheckBox.toggle() }
                .show()
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

    class Builder {

//        var leave: LeaveAndType = LeaveAndType()
//        var mode: String = "user"
//
//        fun setLeave(leave: LeaveAndType): Builder {
//            this.leave = leave
//            return this
//        }
//
//        fun setMode(mode: String): Builder {
//            this.mode = mode
//            return this
//        }
//
//        fun build(): LeaveDialogFragment {
//            return LeaveDialogFragment.newInstance(leave, mode)
//        }
    }

    companion object {
        fun newInstance(leave: LeaveAndType, mode: String): LeaveDialogFragment {

            return LeaveDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_KEY_LEAVE, leave)
                    putString(ARG_KEY_MODE, mode)
                }
            }
        }
    }


}

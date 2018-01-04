package com.vertice.teepop.leaveapp.presentation.adapter

import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import com.vertice.teepop.leaveapp.databinding.ListItemLeaveBinding
import com.vertice.teepop.leaveapp.util.Constant
import kotlin.properties.Delegates

/**
 * Created by VerDev06 on 12/27/2017.
 */
class LeaveAdapter : RecyclerView.Adapter<LeaveAdapter.LeaveHolder>() {

    val TAG: String = this::class.java.simpleName

    var leaves: MutableList<LeaveAndType> by Delegates
            .observable(ArrayList()) { _, _, _ ->
                notifyDataSetChanged()
            }

    val approved: MutableList<Boolean> by lazy {
        leaves.map { it.leave.approve == 1 }.toMutableList()
    }
    var approveChange: MutableMap<Int, Int> = HashMap()

    var mode: String = Constant.MODE_USER

    override fun getItemCount(): Int {
        return leaves.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LeaveAdapter.LeaveHolder {
        val binding = ListItemLeaveBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return LeaveHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaveAdapter.LeaveHolder?, position: Int) {
        holder?.bindData(leaves[position], mode, addApproveChange, position)
    }

    private val addApproveChange: (Int, Int, Int) -> Unit = { leaveId, approve, position ->
        Log.i(TAG, "CheckedChange at Id: $leaveId Boolean: $approve")
        approveChange.apply {
            if (containsKey(leaveId)) {
                remove(leaveId)
            } else {
                put(leaveId, approve)
            }
        }

        leaves[position].leave.approve = approve
    }

    class LeaveHolder(val binding: ListItemLeaveBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(leaveAndType: LeaveAndType, mMode: String, addApproveChange: (Int, Int, Int) -> Unit, position: Int) {
            with(binding) {
                item = leaveAndType
                mode = mMode

                checkApprovedCardView.setOnClickListener {
                    if (it is AppCompatCheckBox) {
                        addApproveChange.invoke(leaveAndType.leave.id, if (it.isChecked) 1 else 0, position)

                    }
                }
                executePendingBindings()
            }
        }
    }

}
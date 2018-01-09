package com.vertice.teepop.leaveapp.presentation.adapter

import android.support.v7.widget.RecyclerView
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

    var leaves: List<LeaveAndType> by Delegates
            .observable(ArrayList()) { _, _, _ ->
                notifyDataSetChanged()
            }
//    var approveChange: MutableMap<Int, String> = HashMap()

    var mode: String = Constant.MODE_USER

    var onCardViewClickListener: ((Int, Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return leaves.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LeaveAdapter.LeaveHolder {
        val binding = ListItemLeaveBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return LeaveHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaveAdapter.LeaveHolder?, position: Int) {
        holder?.bindData(leaves[position], mode, onCardViewClickListener, position)
    }

    class LeaveHolder(val binding: ListItemLeaveBinding) : RecyclerView.ViewHolder(binding.root) {

        val TAG: String = this::class.java.simpleName

        fun bindData(leaveAndType: LeaveAndType, mMode: String, listener: ((Int, Int) -> Unit)?, position: Int) {
            binding.cardView.setOnClickListener {
                listener?.invoke(leaveAndType.leave.id, position)
            }

            binding.includeContentCardView?.apply {
                item = leaveAndType
                mode = mMode

                executePendingBindings()
            }
        }
    }
}



//                checkApprovedCardView2.setOnClickListener {
//                    if (it is AppCompatCheckBox) {
//                        addApproveChange?.invoke(leaveAndType.leave.id, it.isChecked, position)
//                    }
//                }
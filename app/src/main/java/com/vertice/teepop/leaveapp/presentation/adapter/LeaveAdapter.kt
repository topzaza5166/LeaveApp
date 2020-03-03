package com.vertice.teepop.leaveapp.presentation.adapter

import androidx.paging.PagedListAdapter
import android.content.Context
import android.content.DialogInterface
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import com.vertice.teepop.leaveapp.databinding.ListItemLeaveBinding
import com.vertice.teepop.leaveapp.util.Constant
import com.vertice.teepop.leaveapp.util.CustomItemTouchHelperCallback
import kotlin.properties.Delegates

/**
 * Created by VerDev06 on 12/27/2017.
 */
class LeaveAdapter : PagedListAdapter<LeaveAndType, LeaveAdapter.LeaveHolder>(DIFF_CALLBACK) {

    val TAG: String = this::class.java.simpleName

    var mode: String = Constant.MODE_USER

    var onCardViewClickListener: ((LeaveAndType, Int) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        return getItem(position)?.leave?.id ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveHolder {
        val binding = ListItemLeaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaveHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaveHolder, position: Int) {
        holder.bindData(getItem(position), mode, onCardViewClickListener, position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LeaveAndType>() {
            override fun areItemsTheSame(oldItem: LeaveAndType, newItem: LeaveAndType): Boolean =
                    oldItem.leave.id == newItem.leave.id

            override fun areContentsTheSame(oldItem: LeaveAndType, newItem: LeaveAndType): Boolean =
                    oldItem.equals(newItem)
        }
    }

    class LeaveHolder(val binding: ListItemLeaveBinding) : RecyclerView.ViewHolder(binding.root) {

        val TAG: String = this::class.java.simpleName

        fun bindData(leaveAndType: LeaveAndType?, mMode: String, listener: ((LeaveAndType, Int) -> Unit)?, position: Int) {
            binding.cardView.setOnClickListener {
                leaveAndType?.let {
                    listener?.invoke(leaveAndType, position)
                }
            }

            binding.includeContentCardView?.apply {
                item = leaveAndType
                mode = mMode

                executePendingBindings()
            }
        }
    }

}


//    var leaves: List<LeaveAndType> by Delegates
//            .observable(ArrayList()) { _, _, _ ->
//                notifyDataSetChanged()
//            }
//    var approveChange: MutableMap<Int, String> = HashMap()

//                checkApprovedCardView2.setOnClickListener {
//                    if (it is AppCompatCheckBox) {
//                        addApproveChange?.invoke(leaveAndType.leave.id, it.isChecked, position)
//                    }
//                }
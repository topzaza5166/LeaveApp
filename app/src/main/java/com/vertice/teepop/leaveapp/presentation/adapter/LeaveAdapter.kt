package com.vertice.teepop.leaveapp.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.LeaveAndType
import kotlinx.android.synthetic.main.list_item_leave.view.*
import java.text.SimpleDateFormat
import java.util.*

import kotlin.properties.Delegates

/**
 * Created by VerDev06 on 12/27/2017.
 */
class LeaveAdapter : RecyclerView.Adapter<LeaveAdapter.LeaveHolder>() {

    var leaves: List<LeaveAndType> by Delegates
            .observable(ArrayList()) { _, _, _ -> notifyDataSetChanged() }

    override fun getItemCount(): Int {
        return leaves.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LeaveAdapter.LeaveHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_leave, parent, false)
        return LeaveHolder(view)
    }

    override fun onBindViewHolder(holder: LeaveAdapter.LeaveHolder?, position: Int) {
        holder?.bindData(leaves[position])
    }

    class LeaveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindData(leaveAndType: LeaveAndType) = with(itemView) {
            textTypeLeaveCardView.text = leaveAndType.type[0].typeName
            if (leaveAndType.type[0].typeName == "Late arrival") {
                setTimeLate(leaveAndType.leave.timeLate)
            } else {
                setGroupLeave(leaveAndType.leave.fromDate, leaveAndType.leave.toDate)
            }

            textReasonCardView.text = "Reason: ${leaveAndType.leave.reason}"

            checkManagerCardView.isChecked = true
            checkApprovedCardView.isChecked = true
        }

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        private fun View.setGroupLeave(fromDate: Date, toDate: Date) {
            val fromDateFormat = SimpleDateFormat("dd/MM/yyyy")
            textFromDateCardView.text = "From: " + fromDateFormat.format(fromDate)

            val toDateFormat = SimpleDateFormat("dd/MM/yyyy")
            textToDateCardView.text = "To: " + toDateFormat.format(toDate)

            val fromCalendar = Calendar.getInstance().apply { time = fromDate }
            val toCalendar = Calendar.getInstance().apply { time = toDate }

            val diff = (toCalendar.timeInMillis - fromCalendar.timeInMillis) / (24 * 60 * 60 * 1000)
            textTotalCardView.text = "Total : $diff Days"

        }

        private fun View.setTimeLate(timeLate: Int) {
            groupLeaveCardView.visibility = View.GONE
            timeLateCardView.visibility = View.VISIBLE

            val lateString = "Time Late : ${timeLate / 60} Hour ${timeLate % 60} Minute"
            timeLateCardView.text = lateString
        }
    }

}
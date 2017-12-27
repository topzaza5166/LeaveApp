package com.vertice.teepop.leaveapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.entity.Leave
import com.vertice.teepop.leaveapp.data.entity.TypeLeave
import kotlinx.android.synthetic.main.list_item_leave.view.*

import kotlin.properties.Delegates

/**
 * Created by VerDev06 on 12/27/2017.
 */
class LeaveAdapter : RecyclerView.Adapter<LeaveAdapter.LeaveHolder>() {

    var leaves: List<Leave> by Delegates
            .observable(ArrayList()) { _, _, _ -> notifyDataSetChanged() }

    var listType: List<TypeLeave> by Delegates.
            observable(ArrayList()) { _, _, _ -> notifyDataSetChanged() }

    override fun getItemCount(): Int {
        return leaves.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LeaveAdapter.LeaveHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_leave, parent, false)
        return LeaveHolder(view)
    }

    override fun onBindViewHolder(holder: LeaveAdapter.LeaveHolder?, position: Int) {
        holder?.bindData(leaves[position], listType)
    }

    class LeaveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(leave: Leave, type: List<TypeLeave>) = with(itemView) {
            textTypeLeave.text = type.find { it.id == leave.typeId }?.typeName
        }
    }

}
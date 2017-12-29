package com.vertice.teepop.leaveapp.presentation.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.adapter.LeaveAdapter
import com.vertice.teepop.leaveapp.presentation.viewmodel.LeaveViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

import kotlinx.android.synthetic.main.fragment_leave_admin.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveAdminFragment : Fragment() {

    val TAG: String = this::class.java.simpleName

    var leaveAdapter = LeaveAdapter()

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
        return inflater.inflate(R.layout.fragment_leave_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances(view, savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        leaveAdapter.mode = "admin"

        getLeave()

        val mLayoutManager = LinearLayoutManager(context)
        leaveRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = AlphaInAnimationAdapter(leaveAdapter)
        }

        swipeRefreshLayout.setOnRefreshListener {
            getLeave()
            leaveRecyclerView.invalidate()
        }

    }

    fun postApproved() {
        viewModel.postApproved(leaveAdapter.approveChange)
        Log.i(TAG, leaveAdapter.approveChange.toString())
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

    private fun getLeave() {
        viewModel.getLeaveAndType().observe(this, Observer {

            if (swipeRefreshLayout.isRefreshing)
                swipeRefreshLayout.isRefreshing = false

            it?.let {
                Log.i(TAG, "$it")
                leaveAdapter.leaves = it
            }
        })
    }

    companion object {

        fun newInstance(): LeaveAdminFragment {
            return LeaveAdminFragment().apply {
                arguments = Bundle().apply {

                }
            }
        }
    }

}

package com.vertice.teepop.leaveapp.presentation.leave

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.adapter.LeaveAdapter
import kotlinx.android.synthetic.main.fragment_leave_list.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveListFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_leave_list, container, false)
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
        viewModel.getAllLeave().observe(this, Observer {
            it?.let {
                leaveAdapter.leaves = it
            }
        })
        viewModel.getAllType().observe(this, Observer {
            it?.let {
                leaveAdapter.listType = it
            }
        })

        val mLayoutManager = LinearLayoutManager(context)
        leaveRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = leaveAdapter
            addItemDecoration(DividerItemDecoration(context, mLayoutManager.orientation))
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

    companion object {

        fun newInstance(): LeaveListFragment {
            val fragment = LeaveListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}

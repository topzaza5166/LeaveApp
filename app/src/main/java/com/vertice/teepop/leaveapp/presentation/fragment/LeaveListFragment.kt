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
import com.vertice.teepop.leaveapp.util.Constant.KEY_ARG_USER_ID
import kotlinx.android.synthetic.main.fragment_leave_list.*


/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveListFragment : Fragment() {

    val TAG: String = this::class.java.simpleName

    var leaveAdapter = LeaveAdapter()
    var userId: Int = 0

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
        arguments?.let {
            userId = it.getInt(KEY_ARG_USER_ID)
            Log.i(TAG, "id after restore from arg = $userId")
        }
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        getLeave()
        leaveAdapter.onCardViewClickListener = { leave, _ ->
            LeaveDialogFragment
                    .newInstance(leave, "user")
                    .show(childFragmentManager, LeaveDialogFragment::class.java.simpleName)
        }

        val mLayoutManager = LinearLayoutManager(context)
        leaveRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = leaveAdapter
//            addItemDecoration(DividerItemDecoration(context, mLayoutManager.orientation))
        }

        swipeRefreshLayout.setOnRefreshListener {
            getLeave()
            leaveRecyclerView.invalidate()
        }
    }

    private fun getLeave() {
        viewModel.getLeaveByUserId(userId).observe(this, Observer {

            if (swipeRefreshLayout.isRefreshing)
                swipeRefreshLayout.isRefreshing = false

            it?.let {
                Log.i(TAG, "$it")
                leaveAdapter.leaves = it
            }
        })
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

        fun newInstance(id: Int): LeaveListFragment {
            val fragment = LeaveListFragment()
            val args = Bundle()
            args.putInt(KEY_ARG_USER_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

}

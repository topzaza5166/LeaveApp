package com.vertice.teepop.leaveapp.presentation.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.presentation.adapter.LeaveAdapter
import com.vertice.teepop.leaveapp.presentation.viewmodel.LeaveViewModel
import com.vertice.teepop.leaveapp.util.Constant.KEY_ARG_USER_ID
import com.vertice.teepop.leaveapp.util.CustomItemTouchHelperCallback
import kotlinx.android.synthetic.main.fragment_leave_list.*


/**
 * Created by nuuneoi on 11/16/2014.
 */
class LeaveListFragment : Fragment(), CustomItemTouchHelperCallback.CustomItemTouchHelperListener {

    val TAG: String = this::class.java.simpleName

    var leaveAdapter = LeaveAdapter()
    var userId: Long = 0

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
            userId = it.getLong(KEY_ARG_USER_ID)
            Log.i(TAG, "id after restore from arg = $userId")
        }
    }

    private fun initInstances(rootView: View, savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        getLeave()

        initRecyclerView()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reloadLeave(userId)
        }
    }

    private fun initRecyclerView() {
        leaveAdapter.onCardViewClickListener = { leave, _ ->
            LeaveDialogFragment
                    .newInstance(leave, "user")
                    .show(childFragmentManager, LeaveDialogFragment::class.java.simpleName)
        }

        val callback = CustomItemTouchHelperCallback(this)
        val itemTouchHelper = ItemTouchHelper(callback)

        val mLayoutManager = LinearLayoutManager(context)
        leaveRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = leaveAdapter
            itemTouchHelper.attachToRecyclerView(this)
            //            addItemDecoration(DividerItemDecoration(context, mLayoutManager.orientation))
        }
    }

    private fun getLeave() {
        viewModel.getLeaveByUserId(userId).observe(this, Observer {

            if (swipeRefreshLayout.isRefreshing)
                swipeRefreshLayout.isRefreshing = false

            it?.let {
                Log.i(TAG, "$it")
                leaveAdapter.setList(it)
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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemDismiss(position: Int) {
        AlertDialog.Builder(context!!).apply {
            setTitle("Delete Leave")
            setMessage("Are you sure you want to delete?")
            setPositiveButton("OK") { _, _ ->
//                viewModel.deleteLeave(leaveAdapter.getItemId(position))
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel") { _, _ ->
                getLeave()
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    companion object {

        fun newInstance(id: Long): LeaveListFragment {
            val fragment = LeaveListFragment()
            val args = Bundle()
            args.putLong(KEY_ARG_USER_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

}

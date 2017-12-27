package com.vertice.teepop.leaveapp.presentation.leave

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.orhanobut.hawk.Hawk
import com.vertice.teepop.leaveapp.LeaveApplication
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.Employee

import com.vertice.teepop.leaveapp.util.Constant

import kotlinx.android.synthetic.main.activity_leave.*


class LeaveActivity : AppCompatActivity() {

    private val TAG: String = LeaveActivity::class.java.simpleName

    val employee = Hawk.get<Employee>(Constant.USER_KEY, Employee())

    lateinit var viewModel: LeaveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave)
        setSupportActionBar(toolbar)

        Log.i(TAG, employee.toString())
        supportActionBar?.title = employee.name

        viewModel = ViewModelProviders.of(this).get(LeaveViewModel::class.java)
        viewModel.also {
            LeaveApplication.component.inject(it)
        }

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> LeaveFormFragment.newInstance(employee)
                    1 -> LeaveListFragment.newInstance()
                    else -> LeaveFormFragment.newInstance(employee)
                }
            }

            override fun getCount(): Int {
                return 2
            }

        }

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.text = "FORM"
        tabLayout.getTabAt(1)?.text = "LEAVE"

//        supportFragmentManager.beginTransaction()
//                .add(contentContainer.id, LeaveFormFragment.newInstance(employee))
//                .commit()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Your Type ID Selected is ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}

package com.vertice.teepop.leaveapp.presentation.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import com.vertice.teepop.leaveapp.R
import com.vertice.teepop.leaveapp.data.model.Employee
import com.vertice.teepop.leaveapp.presentation.fragment.LeaveAdminFragment
import com.vertice.teepop.leaveapp.presentation.fragment.LeaveFormFragment
import com.vertice.teepop.leaveapp.presentation.fragment.LeaveListFragment

import com.vertice.teepop.leaveapp.util.Constant

import kotlinx.android.synthetic.main.activity_leave.*


class LeaveActivity : AppCompatActivity() {

    private val TAG: String = LeaveActivity::class.java.simpleName

    val employee = Hawk.get<Employee>(Constant.USER_KEY, Employee())

    var pageNum: Int = 2

//    lateinit var viewModel: LeaveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave)
        setSupportActionBar(toolbar)

        Log.i(TAG, employee.toString())
        supportActionBar?.title = employee.name

        if (employee.roleId == 1)
            pageNum = 3

//        viewModel = ViewModelProviders.of(this).get(LeaveViewModel::class.java)
//        viewModel.also {
//            LeaveApplication.component.inject(it)
//        }

        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(onPageChangeListener)
        tabLayout.setupWithViewPager(viewPager)

        fabDone.setOnClickListener {
            val fragment = (viewPagerAdapter.getRegisteredFragment(2) as? LeaveAdminFragment)

        }
    }

    private val viewPagerAdapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

        val registeredFragments: SparseArray<Fragment> = SparseArray()

        fun getRegisteredFragment(position: Int): Fragment {
            return registeredFragments.get(position)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            registeredFragments.put(position, fragment)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "FORM"
                1 -> "LEAVE"
                2 -> "Admin"
                else -> "LEAVE"
            }
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> LeaveFormFragment.newInstance(employee)
                1 -> LeaveListFragment.newInstance(employee.id)
                2 -> LeaveAdminFragment.newInstance(employee.id)
                else -> LeaveFormFragment.newInstance(employee)
            }
        }

        override fun getCount(): Int {
            return pageNum
        }


    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            //TODO: hide and show fab button
//            if (position == 2) {
//                fabDone.show()
//            } else if (fabDone.visibility == View.VISIBLE) {
//                fabDone.hide()
//            } else
//                fabDone.visibility = View.GONE
        }

    }

}

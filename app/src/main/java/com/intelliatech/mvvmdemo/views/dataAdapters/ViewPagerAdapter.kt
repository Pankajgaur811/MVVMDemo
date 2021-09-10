package com.intelliatech.mvvmdemo.views.dataAdapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.intelliatech.mvvmdemo.views.ExpensesFragment
import com.intelliatech.mvvmdemo.views.IncomeFragment

class ViewPagerAdapter(
    val fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment()
            1 -> ExpensesFragment()
            else -> IncomeFragment()
        }
    }


}
package com.intelliatech.mvvmdemo.views.dataAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.intelliatech.mvvmdemo.views.fragments.ExpensesFragment
import com.intelliatech.mvvmdemo.views.fragments.IncomeFragment

class HomeViewPagerAdapter(private val fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {


        return when (position) {
            0 -> IncomeFragment()
            1 -> ExpensesFragment()
            else -> {
                IncomeFragment()
            }

        }
    }
}
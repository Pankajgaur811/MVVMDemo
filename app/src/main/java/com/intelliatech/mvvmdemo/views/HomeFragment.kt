package com.intelliatech.mvvmdemo.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentHomeBinding
import com.intelliatech.mvvmdemo.views.dataAdapters.ViewPagerAdapter


class HomeFragment : Fragment() {

    lateinit var homeBinding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPagerTabbedLayout()
    }

    private fun setUpViewPagerTabbedLayout() {
        // TabLayout
        val tabLayout = homeBinding.tablayout
        // ViewPager2
        val viewPager = homeBinding.viewPager

        viewPager.adapter = ViewPagerAdapter(this)
        // Bind tabs and viewpager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Income"
                1 -> tab.text = "Expenses"

            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
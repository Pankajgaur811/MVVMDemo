package com.intelliatech.mvvmdemo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.intelliatech.mvvmdemo.databinding.FragmentHomeBinding
import com.intelliatech.mvvmdemo.views.dataAdapters.HomeViewPagerAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPagerTabbedLayout()
    }

    private fun setUpViewPagerTabbedLayout() {
        // TabLayout
        val tabLayout = binding.tablayout
        // ViewPager2
        val viewPager = binding.viewPager

        viewPager.adapter = HomeViewPagerAdapter(this)
        // Bind tabs and viewpager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Income"
                1 -> tab.text = "Expenses"

            }
        }.attach()
    }
}
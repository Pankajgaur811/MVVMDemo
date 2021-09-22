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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DhasBoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpViewPagerTabbedLayout()
    }

    private fun setUpViewPagerTabbedLayout() {
        // TabLayout
        val tabLayout = binding.tablayout
        // ViewPager2
        val viewPager = binding.viewPager

        viewPager?.adapter = HomeViewPagerAdapter(this)
        // Bind tabs and viewpager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Income"
                1 -> tab.text = "Expenses"

            }
        }.attach()
    }
}
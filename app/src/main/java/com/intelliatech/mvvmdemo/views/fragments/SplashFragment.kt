package com.intelliatech.mvvmdemo.views.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentSplashBinding
import com.intelliatech.mvvmdemo.models.prefrencesManager.SharePreferenceManager

class SplashFragment : Fragment() {

    private val TAG: String = SplashFragment::class.java.name
    private lateinit var prefManager: SharePreferenceManager
    private lateinit var binding: FragmentSplashBinding
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
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        varInitialize()
        gotoIncomeFragment()
    }

    private fun varInitialize() {
        prefManager = SharePreferenceManager(requireContext())

    }


    private fun gotoIncomeFragment() {
        Handler(Looper.myLooper()!!).postDelayed({

            try {

                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } catch (illegalStateException: IllegalStateException) {
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

}
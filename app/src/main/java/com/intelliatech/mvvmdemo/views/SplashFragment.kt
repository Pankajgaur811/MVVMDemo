package com.intelliatech.mvvmdemo.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentSplashBinding
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import com.intelliatech.mvvmdemo.viewmodel.ExpensesCategoryViewModel
import com.intelliatech.mvvmdemo.viewmodel.IncomeCategoryViewModel
import com.intelliatech.mvvmdemo.viewmodel.PaymentMethodViewModel
import java.lang.IllegalStateException

class SplashFragment : Fragment() {

    private lateinit var paymentMethodViewModel: PaymentMethodViewModel
    private lateinit var expensesCategoryViewModel: ExpensesCategoryViewModel
    private lateinit var incomeCategoryViewmodel: IncomeCategoryViewModel
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        varInitialize()


        insertIncomeCategoryData()
        insertExpensesCategoryData()
        insertPaymentMethodData()
        gotoIncomeFragment()
    }

    private fun varInitialize() {

        incomeCategoryViewmodel = ViewModelProvider(this).get(IncomeCategoryViewModel::class.java)
        expensesCategoryViewModel =
            ViewModelProvider(this).get(ExpensesCategoryViewModel::class.java)
        paymentMethodViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
    }

    private fun insertPaymentMethodData() {
        val paymentMethodEntity = PaymentMethodEntity(1, "Cash")
        val paymentMethodEntity1 = PaymentMethodEntity(2, "UPI")
        val paymentMethodEntity2 = PaymentMethodEntity(3, "Paytm")
        val paymentMethodEntity3 = PaymentMethodEntity(4, "Gpay")
        val paymentMethodEntity4 = PaymentMethodEntity(6, "Check")
        val paymentMethodEntity5 = PaymentMethodEntity(7, "Phone pe")
        val paymentMethodEntity6 = PaymentMethodEntity(8, "Other")

        val paymentMethodList = listOf(
            paymentMethodEntity,
            paymentMethodEntity1,
            paymentMethodEntity2,
            paymentMethodEntity3,
            paymentMethodEntity4,
            paymentMethodEntity5,
            paymentMethodEntity6
        )

        paymentMethodViewModel.insertPaymentMethodList(requireContext(), paymentMethodList)
    }

    private fun insertExpensesCategoryData() {
        val expensesCategoryEntity1 = ExpensesCategoryEntity(1, "Travel")
        val expensesCategoryEntity2 = ExpensesCategoryEntity(2, "Family")
        val expensesCategoryEntity3 = ExpensesCategoryEntity(3, "Party")
        val expensesCategoryEntity4 = ExpensesCategoryEntity(4, "Travel")
        val expensesCategoryList = listOf(
            expensesCategoryEntity1,
            expensesCategoryEntity2,
            expensesCategoryEntity3,
            expensesCategoryEntity4

        )
        expensesCategoryViewModel.insertAllData(requireContext(), expensesCategoryList)
    }

    private fun gotoIncomeFragment() {
        Handler(Looper.myLooper()!!).postDelayed({

            try {

                findNavController().navigate(R.id.action_splashFragment_to_incomeFragment)
            } catch (illegalStateException: IllegalStateException) {
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

    private fun insertIncomeCategoryData() {

        val incomeCategoryEntity1 = IncomeCategoryEntity(1, "Allowance")
        val incomeCategoryEntity2 = IncomeCategoryEntity(2, "Bonus")
        val incomeCategoryEntity3 = IncomeCategoryEntity(3, "Incentives")
        val incomeCategoryEntity4 = IncomeCategoryEntity(4, "Salary")
        val incomeCategoryEntityList = listOf(
            incomeCategoryEntity1,
            incomeCategoryEntity2,
            incomeCategoryEntity3,
            incomeCategoryEntity4
        )
        incomeCategoryViewmodel.insertAllData(requireContext(), incomeCategoryEntityList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SplashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SplashFragment().apply {
            }
    }


}
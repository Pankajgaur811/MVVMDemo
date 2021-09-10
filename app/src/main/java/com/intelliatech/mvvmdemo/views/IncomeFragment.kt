package com.intelliatech.mvvmdemo.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentIncomeBinding
import com.intelliatech.mvvmdemo.viewmodel.IncomeCategoryViewModel
import java.util.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.of
import com.intelliatech.mvvmdemo.viewmodel.ExpensesCategoryViewModel
import com.intelliatech.mvvmdemo.viewmodel.PaymentMethodViewModel
import java.lang.Exception


class IncomeFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private val TAG: String? = "IncomeFragment"
    private var isSelected: Int = 0
    private var sDay: Int = 0
    private var sYear: Int = 0
    private var sMonth: Int = 0
    private var year: Int = 0
    private var day: Int = 0
    private var month: Int = 0
    lateinit var binding: FragmentIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayout.tvIncomeFragment.background =
            resources.getDrawable(R.drawable.selected_tab_bg)
        setClickEvent()
    }



    private fun setClickEvent() {
        binding.checkbox.setOnClickListener(this)
        binding.fabAdd.setOnClickListener(this)
        binding.sortingLayout.btnSearch.setOnClickListener(this)
        binding.sortingLayout.etStartDate.setOnClickListener(this)
        binding.sortingLayout.etEndDate.setOnClickListener(this)
        binding.tabLayout.tvIncomeFragment.setOnClickListener(this)
        binding.tabLayout.tvExpensesFragment.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * @return A new instance of fragment IncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            IncomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    private fun showSortingLayout() {
        if (!!binding.checkbox.isChecked)
            binding.group.visibility = VISIBLE
        else
            binding.group.visibility = GONE

    }

    override fun onStop() {
        super.onStop()
        if (binding.group.visibility === VISIBLE && binding.checkbox.isChecked) {
            binding.group.visibility = GONE
        }
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.checkbox ->
                showSortingLayout()

            R.id.fab_add ->
                navigateCreateFragment()

            R.id.et_start_date -> {
                isSelected = 1
                showDatePicker()
            }

            R.id.et_end_date -> {
                isSelected = 2
                showDatePicker()
            }
            R.id.btn_search ->
                showFilterRecord()

            R.id.tv_expenses_fragment ->
                navigateExpensesFragment()

        }
    }

    private fun navigateExpensesFragment() {
        try {
            findNavController().navigate(R.id.action_incomeFragment_to_expensesFragment2)
        }catch (e :Exception)
        {

        }
    }

    private fun navigateIncomeFragment() {
        TODO("Not yet implemented")
    }

    private fun showFilterRecord() {

    }

    private fun showDatePicker() {
        Log.d("TAG", "show Date picker")
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog =
            context?.let { DatePickerDialog(it, this, year, month, day) }
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog!!.show()

    }

    private fun navigateCreateFragment() {
//        Navigation.findNavController(binding.root)
//            .navigate(R.id.action_incomeFragment_to_createFragment)
        findNavController().navigate(R.id.action_incomeFragment_to_createFragment)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        var timeInMiliSecond = calendar.timeInMillis
        var date = "$dayOfMonth - $month - $year"

        when (isSelected) {

            1 -> {
                binding.sortingLayout.etStartDate.text =
                    Editable.Factory.getInstance().newEditable(date)
                isSelected = 0
            }
            2 -> {
                isSelected = 0
                binding.sortingLayout.etEndDate.text =
                    Editable.Factory.getInstance().newEditable(date)
            }
        }


    }

}
package com.intelliatech.mvvmdemo.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentIncomeBinding
import java.util.*

class ExpensesFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private var year: Int = 0
    private var day: Int = 0
    private var month: Int = 0
    private var isSelected: Int = 0
    private lateinit var binding: FragmentIncomeBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        return binding.root
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
            binding.group.visibility = View.VISIBLE
        else
            binding.group.visibility = View.GONE

    }

    override fun onStop() {
        super.onStop()
        if (binding.group.visibility === View.VISIBLE && binding.checkbox.isChecked) {
            binding.group.visibility = View.GONE
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
        findNavController().navigate(R.id.action_incomeFragment_to_expensesFragment2)
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
package com.intelliatech.mvvmdemo.views.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentIncomeBinding
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.viewmodel.IncomeExpensesViewModel
import com.intelliatech.mvvmdemo.views.clickEventListeners.SingleClickEventList
import com.intelliatech.mvvmdemo.views.dataAdapters.IncomeExpensesListDataAdapter
import java.util.*


class IncomeFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    SingleClickEventList {


    private lateinit var start_date: String
    private lateinit var end_date: String
    private var totalBalance: Int = 0
    private var totalIncome: Int = 0
    private var totalExpenses: Int = 0
    private var incomeRecordList = arrayListOf<IncomeExpensesEntity>()
    private var incomeExpensesViewModel: IncomeExpensesViewModel? = null
    private val TAG: String? = "IncomeFragment"
    private var isSelected: Int = 0
    private val viewType: Int =
        0//viewType vlaue set zero for get all income record list from database
    private var year: Int = 0
    private var day: Int = 0
    private var month: Int = 0
    lateinit var binding: FragmentIncomeBinding
    lateinit var incomeListAdapter: IncomeExpensesListDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)
        initVar()
        return binding.root
    }

    private fun initVar() {

        incomeExpensesViewModel = ViewModelProvider(this).get(IncomeExpensesViewModel::class.java)

        getTotalIncome()
        getTotalExpenses()
        fetchIncomeList()
    }


    private fun setTotalBalance() {
        totalBalance = totalIncome - totalExpenses
        binding.showStatus.tvTotalBalance.text = totalBalance.toString()
    }

    private fun getTotalExpenses() {
        incomeExpensesViewModel?.getTotalAmount(requireContext(), 1)?.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    Log.d(TAG, "total Income " + it)
                    binding.showStatus.tvTotalExpenses.text = it.toString()
                    totalExpenses = it

                }
                setTotalBalance()
            })
    }

    private fun getTotalIncome() {
        incomeExpensesViewModel?.getTotalAmount(requireContext(), 0)?.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                if (it != null) {
                    Log.d(TAG, "total Income " + it)
                    binding.showStatus.tvTotalIncome.text = it.toString()
                    totalIncome = it
                }

                setTotalBalance()
            })

    }

    private fun fetchIncomeList() {
        incomeExpensesViewModel?.getAllRecordList(requireContext(), viewType)
            ?.observe(viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    Log.d(TAG, "IncomeList data" + it.toString())
                    setIncomeListAdapter(it)

                })

    }

    private fun setIncomeListAdapter(it: List<IncomeExpensesEntity>) {
        incomeRecordList.addAll(it)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        incomeListAdapter = IncomeExpensesListDataAdapter(requireContext(), incomeRecordList, this)
        binding.rvList.adapter = incomeListAdapter
        incomeListAdapter.notifyDataSetChanged()

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
        else {
            binding.group.visibility = GONE
        }

    }

    override fun onStop() {
        super.onStop()
        if (binding.group.visibility === VISIBLE && binding.checkbox.isChecked) {
            binding.group.visibility = GONE
        }
        incomeRecordList.clear()
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

        } catch (e: Exception) {

        }
    }

    private fun navigateIncomeFragment() {
        TODO("Not yet implemented")
    }

    private fun showFilterRecord() {
        if (start_date != null) {
            if (end_date != null) {

                incomeExpensesViewModel?.getDataBetweenTwoDates(
                    requireContext(),
                    start_date,
                    end_date, viewType

                )?.observe(viewLifecycleOwner,
                    androidx.lifecycle.Observer {
                        Log.d(TAG, "Sorted list :- " + it.toString())
                        incomeRecordList.clear()
                        incomeRecordList.addAll(it)
                        incomeListAdapter.notifyDataSetChanged()
                    })

            } else
                Toast.makeText(requireContext(), "please select End date", Toast.LENGTH_SHORT)
                    .show()
        } else
            Toast.makeText(requireContext(), "please select start date", Toast.LENGTH_SHORT).show()

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
        val action =
            com.intelliatech.mvvmdemo.views.fragments.IncomeFragmentDirections.actionIncomeFragmentToCreateFragment()
                .setViewType(viewType)
        action.setGetData(null)
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        var dateInMiliSecond = calendar.timeInMillis
        var timeInMiliSecond = calendar.timeInMillis
        var date = "$dayOfMonth - $month - $year"

        when (isSelected) {

            1 -> {
                binding.sortingLayout.etStartDate.text =
                    Editable.Factory.getInstance().newEditable(date)
                isSelected = 0
                start_date = dateInMiliSecond.toString()
            }
            2 -> {
                isSelected = 0
                binding.sortingLayout.etEndDate.text =
                    Editable.Factory.getInstance().newEditable(date)
                end_date = dateInMiliSecond.toString()
            }
        }
    }

    override fun clickSingleRecordList(incomeExpensesEntity: IncomeExpensesEntity, view: View) {
        val action =
            com.intelliatech.mvvmdemo.views.fragments.IncomeFragmentDirections.actionIncomeFragmentToCreateFragment()
                .setViewType(viewType)
        action.setGetData(incomeExpensesEntity)
        Navigation.findNavController(binding.root).navigate(action)
    }


}
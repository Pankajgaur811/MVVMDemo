package com.intelliatech.mvvmdemo.views.fragments

import android.annotation.SuppressLint
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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentIncomeBinding
import com.intelliatech.mvvmdemo.dependenciesInjection.container.ModuleProvider
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.views.clickEventListeners.SingleClickEventList
import com.intelliatech.mvvmdemo.views.dataAdapters.IncomeExpensesListDataAdapter
import org.koin.core.component.KoinApiExtension
import java.util.*

@KoinApiExtension
class IncomeFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    SingleClickEventList {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    private var startDate: String? = null
    private var endDate: String? = null
    private var totalBalance: Int = 0
    private var totalIncome: Int = 0
    private var totalExpenses: Int = 0

    private var incomeRecordList = arrayListOf<IncomeExpensesEntity>()
    private val TAG: String = IncomeFragment::class.java.simpleName
    private var isSelected: Int = 0
    private val viewType: Int =
        0//viewType value set zero for get all income record list from database
    private var year: Int = 0
    private var day: Int = 0
    private var month: Int = 0
    private var incomeListAdapter: IncomeExpensesListDataAdapter? = null

    private val moduleProvider = ModuleProvider()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        initVar()
        return binding.root
    }

    private fun initVar() {
        getTotalIncome()
        getTotalExpenses()
        fetchIncomeList()
    }


    private fun setTotalBalance() {
        totalBalance = totalIncome - totalExpenses
        binding.showStatus.tvTotalBalance.text = totalBalance.toString()
    }

    private fun getTotalExpenses() {
        moduleProvider.incomeExpensesVM.getTotalAmount(1)?.observe(viewLifecycleOwner,
            {
                if (it != null) {
                    Log.d(TAG, "total Income $it")
                    binding.showStatus.tvTotalExpenses.text = it.toString()
                    totalExpenses = it

                }
                setTotalBalance()
            })
    }

    private fun getTotalIncome() {
        moduleProvider.incomeExpensesVM.getTotalAmount(0)?.observe(viewLifecycleOwner,
            {
                if (it != null) {
                    Log.d(TAG, "total Income $it")
                    binding.showStatus.tvTotalIncome.text = it.toString()
                    totalIncome = it
                }

                setTotalBalance()
            })

    }

    private fun fetchIncomeList() {
        moduleProvider.incomeExpensesVM.getAllRecordList(viewType)?.observe(viewLifecycleOwner,
            {
                if (it != null) {
                    Log.d(TAG, "IncomeList data$it")
                    setIncomeListAdapter(it)
                } else
                    Log.d(TAG, "IncomeList data null")
            })

    }

    private fun setIncomeListAdapter(it: List<IncomeExpensesEntity>) {
        incomeRecordList.addAll(it)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        incomeListAdapter = IncomeExpensesListDataAdapter(incomeRecordList, this)
        binding.rvList.adapter = incomeListAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
    }


    private fun setClickEvent() {
        binding.checkbox.setOnClickListener(this)
        binding.fabAdd.setOnClickListener(this)
        binding.sortingLayout.btnSearch.setOnClickListener(this)
        binding.sortingLayout.etStartDate.setOnClickListener(this)
        binding.sortingLayout.etEndDate.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        incomeListAdapter = null
    }

    private fun showSortingLayout() {
        if (binding.checkbox.isChecked)
            binding.group.visibility = VISIBLE
        else {
            binding.group.visibility = GONE
        }
    }

    override fun onStop() {
        super.onStop()
        if (binding.group.visibility == VISIBLE && binding.checkbox.isChecked) {
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

    @SuppressLint("NotifyDataSetChanged")
    private fun showFilterRecord() {
        if (startDate != null) {
            if (endDate != null) {
                moduleProvider.incomeExpensesVM.getDataBetweenTwoDates(
                    startDate!!,
                    endDate!!, viewType

                )?.observe(viewLifecycleOwner,
                    {
                        Log.d(TAG, "Sorted list :- $it")
                        incomeRecordList.clear()
                        incomeRecordList.addAll(it)
                        incomeListAdapter?.notifyDataSetChanged()
                    })

            } else
                Toast.makeText(requireContext(), "please select End date", Toast.LENGTH_SHORT)
                    .show()
        } else
            Toast.makeText(requireContext(), "please select start date", Toast.LENGTH_SHORT).show()

    }

    private fun showDatePicker() {
        Log.d(TAG, "show Date picker")
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
            HomeFragmentDirections.actionHomeFragmentToCreateFragment().setViewType(viewType)
        action.getData = null
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateInMilliSecond = calendar.timeInMillis
        val date = "$dayOfMonth - $month - $year"

        when (isSelected) {

            1 -> {
                binding.sortingLayout.etStartDate.text =
                    Editable.Factory.getInstance().newEditable(date)
                isSelected = 0
                startDate = dateInMilliSecond.toString()
            }
            2 -> {
                isSelected = 0
                binding.sortingLayout.etEndDate.text =
                    Editable.Factory.getInstance().newEditable(date)
                endDate = dateInMilliSecond.toString()
            }
        }
    }

    override fun clickSingleRecordList(incomeExpensesEntity: IncomeExpensesEntity, view: View) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCreateFragment().setViewType(viewType)
        action.getData = incomeExpensesEntity
        Navigation.findNavController(binding.root).navigate(action)
    }
}
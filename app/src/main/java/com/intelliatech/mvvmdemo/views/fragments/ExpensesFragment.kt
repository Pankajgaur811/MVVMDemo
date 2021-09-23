package com.intelliatech.mvvmdemo.views.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
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
class ExpensesFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    SingleClickEventList {

    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!
    private var expensesRecordList = arrayListOf<IncomeExpensesEntity>()
    private lateinit var start_date: String
    private lateinit var end_date: String
    private val TAG = ExpensesFragment::class.java.simpleName
    private val viewType: Int = 1
    //viewType value set one for get all expenses record list from database
    private val modelProvider = ModuleProvider()

    private var expensesListAdapter: IncomeExpensesListDataAdapter? = null

    private var totalBalance: Int = 0
    private var totalExpenses: Int = 0
    private var totalIncome: Int = 0
    private var isSelected: Int = 0

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
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setClickEvent()
        initVar()
    }

    private fun initVar() {
      getTotalIncome()
        getTotalExpenses()

        fetchExpensesList()
    }

    private fun setTotalBalance() {
        totalBalance = totalIncome - totalExpenses
        binding.showStatus.tvTotalBalance.text = totalBalance.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        expensesListAdapter = null
    }


    private fun getTotalExpenses() {
        modelProvider.incomeExpensesVM.getTotalAmount(viewType)
            ?.observe(viewLifecycleOwner,
              {
                    if (it != null) {
                        Log.d(TAG, "total Income " + it)
                        binding.showStatus.tvTotalExpenses.text = it.toString()
                        totalExpenses = it

                    }
                    setTotalBalance()
                })
    }

    private fun getTotalIncome() {
        modelProvider.incomeExpensesVM.getTotalAmount(0)?.observe(viewLifecycleOwner,
         {
                if (it != null) {
                    Log.d(TAG, "total Income " + it)
                    binding.showStatus.tvTotalIncome.text = it.toString()
                    totalIncome = it
                }

                setTotalBalance()
            })

    }


    private fun fetchExpensesList() {
        modelProvider.incomeExpensesVM.getAllRecordList(viewType)
            ?.observe(viewLifecycleOwner,
             {
                    Log.d(TAG, "Ecxpenses data" + it.toString())
                    setExpensesListAdapter(it)
                })
    }

    private fun setExpensesListAdapter(it: List<IncomeExpensesEntity>) {
        expensesRecordList.addAll(it)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        expensesListAdapter =
            IncomeExpensesListDataAdapter(expensesRecordList, this)
        binding.rvList.adapter = expensesListAdapter
        expensesListAdapter?.notifyDataSetChanged()

    }

    private fun setClickEvent() {
        binding.checkbox.setOnClickListener(this)
        binding.fabAdd.setOnClickListener(this)
        binding.sortingLayout.btnSearch.setOnClickListener(this)
        binding.sortingLayout.etStartDate.setOnClickListener(this)
        binding.sortingLayout.etEndDate.setOnClickListener(this)
    }

    private fun showSortingLayout() {
        if (binding.checkbox.isChecked)
            binding.group.visibility = View.VISIBLE
        else
            binding.group.visibility = View.GONE

    }

    override fun onStop() {
        super.onStop()
        if (binding.group.visibility == View.VISIBLE && binding.checkbox.isChecked) {
            binding.group.visibility = View.GONE
        }
        expensesRecordList.clear()
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

        }
    }

    private fun showFilterRecord() {
        if (start_date != null) {
            if (end_date != null) {
                modelProvider.incomeExpensesVM.getDataBetweenTwoDates(
                    start_date,
                    end_date, viewType
                )?.observe(viewLifecycleOwner,
                    androidx.lifecycle.Observer {
                        Log.d(TAG, "Sorted list :- " + it.toString())
                        expensesRecordList.clear()
                        expensesRecordList.addAll(it)
                        expensesListAdapter?.notifyDataSetChanged()
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

        val datePickerDialog =
            context?.let {
                DatePickerDialog(
                    it,
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            }
        datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
        datePickerDialog!!.show()

    }

    private fun navigateCreateFragment() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCreateFragment().setViewType(viewType)
        action.setGetData(null)
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        var dateInMiliSecond = calendar.timeInMillis
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
            HomeFragmentDirections.actionHomeFragmentToCreateFragment().setViewType(viewType)
        action.setGetData(incomeExpensesEntity)
        Navigation.findNavController(binding.root).navigate(action)
    }

}
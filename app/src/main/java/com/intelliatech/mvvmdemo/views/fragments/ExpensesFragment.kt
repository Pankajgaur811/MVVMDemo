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

class ExpensesFragment : Fragment(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    SingleClickEventList {

    private var expensesRecordList = arrayListOf<IncomeExpensesEntity>()
    private lateinit var start_date: String
    private lateinit var end_date: String
    private val TAG = "ExpensesFragment"
    private val viewType: Int =
        1   //viewType vlaue set one for get all income record list from database
    private var expensesListAdapter: IncomeExpensesListDataAdapter? = null
    private var incomeExpensesViewModel: IncomeExpensesViewModel? = null
    private var totalBalance: Int = 0
    private var totalExpenses: Int = 0
    private var totalIncome: Int = 0
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.tvExpensesFragment.background =
            resources.getDrawable(R.drawable.selected_tab_bg)
        initView()
    }

    private fun initView() {
        setClickEvent()
        initVar()
    }

    private fun initVar() {

        incomeExpensesViewModel =
            ViewModelProvider(this).get(IncomeExpensesViewModel::class.java)

        getTotalIncome()
        getTotalExpenses()

        fetchExpensesList()
    }

    private fun setTotalBalance() {
        totalBalance = totalIncome - totalExpenses
        binding.showStatus.tvTotalBalance.text = totalBalance.toString()
    }

    private fun getTotalExpenses() {
        incomeExpensesViewModel?.getTotalAmount(requireContext(), viewType)
            ?.observe(viewLifecycleOwner,
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

    private fun fetchExpensesList() {
        incomeExpensesViewModel?.getAllRecordList(requireContext(), viewType)
            ?.observe(viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    Log.d(TAG, "Ecxpenses data" + it.toString())
                    setExpensesListAdapter(it)

                })

    }

    private fun setExpensesListAdapter(it: List<IncomeExpensesEntity>) {
        expensesRecordList.addAll(it)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        expensesListAdapter =
            IncomeExpensesListDataAdapter(requireContext(), expensesRecordList, this)
        binding.rvList.adapter = expensesListAdapter
        expensesListAdapter?.notifyDataSetChanged()

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

            R.id.tv_income_fragment ->
                navigatIncomeFragment()

        }
    }

    private fun navigatIncomeFragment() {
        try {
            findNavController().navigate(R.id.action_expensesFragment2_to_incomeFragment)

        } catch (e: Exception) {
            Log.d(TAG, "Exception :- ${e.message}")
        }
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
//        findNavController().navigate(R.id.action_incomeFragment_to_createFragment)
        val action =
            com.intelliatech.mvvmdemo.views.fragments.ExpensesFragmentDirections.actionExpensesFragment2ToCreateFragment()
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
            com.intelliatech.mvvmdemo.views.fragments.ExpensesFragmentDirections.actionExpensesFragment2ToCreateFragment()
                .setViewType(viewType)
        action.setGetData(incomeExpensesEntity)
        Navigation.findNavController(binding.root).navigate(action)
    }
}
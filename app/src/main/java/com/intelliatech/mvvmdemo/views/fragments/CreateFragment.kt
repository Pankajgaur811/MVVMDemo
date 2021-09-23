package com.intelliatech.mvvmdemo.views.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentCreateBinding
import com.intelliatech.mvvmdemo.dependenciesInjection.container.ModuleProvider
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import org.koin.core.component.KoinApiExtension
import java.util.*

@KoinApiExtension
class CreateFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private var incomeExpensesEntity: IncomeExpensesEntity? = null
    private val component = ModuleProvider()

    private var dateInMiliSecond: String = ""
    private var time: String = ""
    private var viewType: Int = 0
    private var isEdit = false
    private val args: CreateFragmentArgs by navArgs()
    private val TAG: String = CreateFragment::class.java.simpleName
    private var paymentMode: String = ""

    //    private var paymentMethodList: MutableList<PaymentMethodEntity>? = null
    var paymentMethodList = arrayListOf<String>()

    var categoryList = arrayListOf<String>()

    // access the items of the list
    val pay = arrayOf("salary", "cash", "online")
    lateinit var binding: FragmentCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        viewType = args.viewType
        if (args.getData != null) {
            incomeExpensesEntity = args.getData
        }
        return binding.root
    }

    private fun setData(incomeExpensesEntity: IncomeExpensesEntity?) {
        isEdit = true
        binding.btnSubmit.text = "Update Record"
        binding.btnGroup.visibility = VISIBLE
        binding.etAmount.text =
            Editable.Factory.getInstance().newEditable(incomeExpensesEntity?.amount.toString())
        binding.etDescription.text =
            Editable.Factory.getInstance().newEditable(incomeExpensesEntity?.description.toString())
        binding.etPayer.text =
            Editable.Factory.getInstance().newEditable(incomeExpensesEntity?.payer.toString())
        binding.etTime.text =
            Editable.Factory.getInstance().newEditable(incomeExpensesEntity?.time.toString())
        binding.etDate.text = Editable.Factory.getInstance().newEditable(
            incomeExpensesEntity?.date?.toLong()?.let {
                component.utilityHelper.convertMiliSecondToDate(
                    "dd-MM-yyyy",
                    it
                )
            }
        )
        dateInMiliSecond = incomeExpensesEntity?.date.toString()
        time = incomeExpensesEntity?.time.toString()
        binding.atvCategoryList.text =
            Editable.Factory.getInstance().newEditable(incomeExpensesEntity?.category.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun addCategorySpinner() {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryList
        )
        binding.atvCategoryList.setAdapter(adapter)
    }

    private fun addPaymentMethodSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, paymentMethodList
        )
        binding.spinPaymentMethodType.adapter = adapter

        if (incomeExpensesEntity != null) {
            var index: Int = 0
            for (value in paymentMethodList) {

                if (value.contentEquals(incomeExpensesEntity?.payment_method))
                    break
                index++
            }
            binding.spinPaymentMethodType.setSelection(index)

        }

        binding.spinPaymentMethodType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View, position: Int, id: Long
                ) {
                    paymentMode = paymentMethodList.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
    }

    private fun initView() {
        setClickListenersEvent()
        setScreenTitle()
        fetchData()
    }


    private fun setScreenTitle() {
        when (viewType) {
            0 -> {

                getIncomeCategoryData()
                if (incomeExpensesEntity == null)
                    binding.tvScreenTitle.text = resources.getString(R.string.txt_income_fragment)
                else {
                    binding.tvScreenTitle.text = getString(R.string.txt_edit_income_record)
                    setData(incomeExpensesEntity)
                }
            }

            1 -> {

                getExpensesCategoryData()
                if (incomeExpensesEntity == null)
                    binding.tvScreenTitle.text = resources.getString(R.string.txt_expenses_fragment)
                else {
                    binding.tvScreenTitle.text = getString(R.string.txt_edit_expenses_record)
                    setData(incomeExpensesEntity)

                }
            }
        }
    }

    private fun setClickListenersEvent() {
        binding.btnDelete.setOnClickListener(this)
        binding.etDate.setOnClickListener(this)
        binding.etTime.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)


    }


    private fun showTimePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            context, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true
        )
        timePickerDialog.show()

    }

    private fun showDatePicker() {
        Log.d("TAG", "show Date picker")
        val calendar = Calendar.getInstance()

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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        dateInMiliSecond = calendar.timeInMillis.toString()
        binding.etDate.text =
            Editable.Factory.getInstance().newEditable("$dayOfMonth - $month - $year")
    }

    override fun onTimeSet(p0: TimePicker?, h: Int, m: Int) {

        // AM_PM decider logic
        var am_pm = ""
        var hour = h
        when {
            hour == 0 -> {
                hour += 12
                am_pm = "AM"
            }
            hour == 12 -> am_pm = "PM"
            hour > 12 -> {
                hour -= 12
                am_pm = "PM"
            }
            else -> am_pm = "AM"


        }
        var shour = if (hour < 10) "0" + hour else hour
        var smin = if (m < 10) "0" + m else m
        time = "$shour : $smin $am_pm"
        binding.etTime.text = Editable.Factory.getInstance().newEditable(time)

    }

    private fun getPaymentMethodData() {
        context?.let {
            component.incomeExpensesVM.getAllPaymentMethodList()
                ?.observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        for (i in it) {

                            paymentMethodList.add(i.paymentMethodName)
                        }
                    } else
                        Log.d(TAG, "payment data is not available" + it.toString())

                    addPaymentMethodSpinner()
                }
                )
        }
    }

    private fun fetchData() {
        getPaymentMethodData()
    }


    private fun getExpensesCategoryData() {
        component.incomeExpensesVM.getExpensesCategoryAllList()
            ?.observe(viewLifecycleOwner, Observer {
                if (it != null) {

                    for (i in it) {

                        categoryList.add(i.category_name)
                    }
                } else
                    Log.d(TAG, "payment data is not available" + it.toString())
                Log.d(TAG, "Expenses category data" + categoryList.toString())
                addCategorySpinner()
            }
            )
    }


    private fun getIncomeCategoryData() {
        component.incomeExpensesVM.getIncomeCategoryList()
            ?.observe(viewLifecycleOwner, Observer {
                if (it != null) {

                    for (i in it) {
                        categoryList.add(i.categotry_name)
                    }
                } else
                    Log.d(TAG, "income category data" + categoryList.toString())
                addCategorySpinner()
            }

            )


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.et_date ->
                showDatePicker()

            R.id.et_time ->
                showTimePicker()

            R.id.btn_submit -> {

                if (isEdit) {
                    updateData()

                } else
                    saveData()
            }
            R.id.btn_delete ->
                deleteData()

        }
    }

    private fun deleteData() {
        incomeExpensesEntity?.let { component.incomeExpensesVM.deleteRecord(it) }
        previousFragment()
    }

    private fun updateData() {
        if (checkValidation()) {
            updatedData()
        }
    }


    private fun updatedData() {

        incomeExpensesEntity?.payment_method = paymentMode
        incomeExpensesEntity?.payer = binding.etPayer.text.toString()
        incomeExpensesEntity?.amount = binding.etAmount.text.toString().toInt()
        incomeExpensesEntity?.category = binding.atvCategoryList.text.toString()
        incomeExpensesEntity?.description = binding.etDescription.text.toString()
        incomeExpensesEntity?.date = dateInMiliSecond
        incomeExpensesEntity?.time = time
        incomeExpensesEntity?.type = viewType

        val valueEffect =
            incomeExpensesEntity?.let {
                component.incomeExpensesVM.updateRecord(
                    it
                )
            }
        Log.d(TAG, "No. of row effected :- $valueEffect")
        previousFragment()
    }

    private fun previousFragment() {
        when (viewType) {
            0 -> {
                try {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_createFragment_to_incomeFragment)
                } catch (e: Exception) {
                    Log.d(TAG, "excption :- ${e.message}")
                }
            }
            1 -> {
                try {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_createFragment_to_expensesFragment2)
                } catch (e: Exception) {
                    Log.d(TAG, "excption :- ${e.message}")
                }
            }

        }
    }

    private fun saveData() {
        if (checkValidation()) {
            insertData()
        }
    }

    private fun insertData() {
        var incomeExpensesEntity = IncomeExpensesEntity(
            Integer.parseInt(binding.etAmount.text.toString()),
            binding.etPayer.text.toString(),
            binding.atvCategoryList.text.toString(),
            paymentMode, dateInMiliSecond, time, binding.etDescription.text.toString(), viewType
        )
        component.incomeExpensesVM.insertRecord(incomeExpensesEntity)
        previousFragment()
    }

    private fun checkValidation(): Boolean {
        if (binding.etAmount.text!!.isEmpty()) {
            Toast.makeText(requireContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (binding.etPayer.text!!.isEmpty()) {
                Toast.makeText(requireContext(), "Please Payer data", Toast.LENGTH_SHORT).show()
                return false
            } else {
                if (binding.etPayer.text!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Payer data", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    if (binding.etDate.text!!.isEmpty()) {
                        Toast.makeText(requireContext(), "Please select Date", Toast.LENGTH_SHORT)
                            .show()
                        return false
                    } else {
                        if (binding.etTime.text!!.isEmpty()) {
                            Toast.makeText(
                                requireContext(),
                                "Please select Time",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            return false
                        } else {
                            if (binding.etDescription.text!!.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "Please Enter Description",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return false
                            } else
                                return true

                        }

                    }
                }
            }
        }
    }
}

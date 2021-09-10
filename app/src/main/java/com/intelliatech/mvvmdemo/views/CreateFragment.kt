package com.intelliatech.mvvmdemo.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.format.DateFormat
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.Observer
import com.intelliatech.mvvmdemo.R
import com.intelliatech.mvvmdemo.databinding.FragmentCreateBinding
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import com.intelliatech.mvvmdemo.viewmodel.ExpensesCategoryViewModel
import com.intelliatech.mvvmdemo.viewmodel.IncomeCategoryViewModel
import com.intelliatech.mvvmdemo.viewmodel.PaymentMethodViewModel

import java.util.*


class CreateFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    private val TAG: String? = "CreateFragment"
    private var sDay: Int = 0
    private var sMonth: Int = 0
    private var sYear: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var year: Int = 0
    private var expensesCategoryViewModel: ExpensesCategoryViewModel? = null
    private var paymentMethodViewModel: PaymentMethodViewModel? = null
    private var incomecategoryViewModel: IncomeCategoryViewModel? = null

    //    private var paymentMethodList: MutableList<PaymentMethodEntity>? = null
    var paymentMethodList = arrayListOf<String>()

    // access the items of the list
    val pay = arrayOf("salary", "cash", "online")
    lateinit var binding: FragmentCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()


        // access the spinner


    }

    private fun addCategorySpinner() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, paymentMethodList
        )
        binding.atvCategoryList.threshold = 1
        binding.atvCategoryList.isCursorVisible = false
        binding.atvCategoryList.setAdapter(adapter)
    }

    private fun addPaymentMethodSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, paymentMethodList
        )
        binding.spinPaymentMethodType.adapter = adapter


        binding.spinPaymentMethodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context,
                    paymentMethodList[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


    private fun initVar() {
        incomecategoryViewModel = ViewModelProvider(this).get(IncomeCategoryViewModel::class.java)
        paymentMethodViewModel = ViewModelProvider(this).get(PaymentMethodViewModel::class.java)
        expensesCategoryViewModel =
            ViewModelProvider(this).get(ExpensesCategoryViewModel::class.java)

    }

    private fun initView() {
        setClickEvent()
        initVar()
        addPaymentMethodSpinner()
        addCategorySpinner()
        fetchdata()
    }

    private fun setClickEvent() {
        binding.etDate.setOnClickListener(this)
        binding.etTime.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            CreateFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun showTimePicker() {
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            context, this, hour, minute,
            DateFormat.is24HourFormat(context)
        )
        timePickerDialog.show()

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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        sDay = dayOfMonth
        sYear = year
        sMonth = month
        val calendar: Calendar = Calendar.getInstance()
        var date = "$sDay - $sMonth - $sYear"

        binding.etDate.text = Editable.Factory.getInstance().newEditable(date)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        var time = "$p1 : $p2"
        binding.etTime.text = Editable.Factory.getInstance().newEditable(time)
    }

    private fun getPaymentMethodData() {
        context?.let {
            paymentMethodViewModel?.getAllPaymentMethodList(it)
                ?.observe(viewLifecycleOwner, Observer {

                    if (it != null) {

                        for (i in it) {

                            paymentMethodList.add(i.paymentMethodName)
                        }
                    } else
                        Log.d(TAG, "payment data is not available" + it.toString())
                }
                )
        }
    }

    private fun fetchdata() {
        getIncomeCategoryData()
        getExpensesCategoryData()
        getPaymentMethodData()
    }


    private fun getExpensesCategoryData() {
        context?.let {
            expensesCategoryViewModel?.getExpensesCategoryListt(it)
                ?.observe(viewLifecycleOwner, Observer {


                    if (it == null)
                        Log.d(TAG, "expenses data is not available")
                    else
                        Log.d(TAG, "expenses data is  available" + it.toString())

                }
                )
        }
    }

    private fun getIncomeCategoryData() {
        context?.let { incomecategoryViewModel?.getIncomeCategoryList(it) }!!
            .observe(viewLifecycleOwner, Observer {
                if (it == null) {

                    Log.d(TAG, "data is not available")
                } else {

                    Log.d(TAG, "data is available :" + it.toString())
                }

            }
            )


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.et_date ->
                showDatePicker()

            R.id.et_time ->
                showTimePicker()

//            R.id.btn_submit ->
//                saveData()

        }
    }

    private fun saveData() {
        TODO("Not yet implemented")
    }


}

package com.intelliatech.mvvmdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeExpensesViewModel(
    val incomeCategoryRepo: IncomeCategoryRepo?,
    val expensesCategoryRepo: ExpensesCategoryRepo?,
    val paymentMethodRepo: PaymentMethodRepo?,
    val incomeExpensesRepo: IncomeExpensesRepo?
) : ViewModel() {

    var totalAmount: LiveData<Int>? = null
    var totalExpenses: LiveData<Int>? = null
    var totalIncome: LiveData<Int>? = null
    var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null

    fun insertRecord(incomeExpensesEntity: IncomeExpensesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            incomeExpensesRepo?.insertRecord(incomeExpensesEntity)
        }
    }


    fun updateRecord(incomeExpensesEntity: IncomeExpensesEntity): Int {
        var value = 0

        viewModelScope.launch(Dispatchers.IO)
        {
            value = incomeExpensesRepo?.updateRecord(incomeExpensesEntity)!!
        }

        return value
    }

    fun getAllRecordList(viewType: Int): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList = incomeExpensesRepo?.getIncomeExpensesRecordList(viewType)
        return incomeExpensesList
    }

    fun getDataBetweenTwoDates(
        start_date: String,
        end_date: String,
        viewType: Int
    ): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList =
            incomeExpensesRepo?.getDataInBetweenDates(start_date, end_date, viewType)
        return incomeExpensesList

    }

    fun deleteRecord(incomeExpensesEntity: IncomeExpensesEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            incomeExpensesRepo?.deleteRecord(incomeExpensesEntity)
        }


    }

    fun getTotalAmount(viewType: Int): LiveData<Int>? {

        when (viewType) {
            0 -> {
                totalIncome = incomeExpensesRepo?.getTotalAmount(viewType)
                return totalIncome
            }
            1 -> {
                totalExpenses = incomeExpensesRepo?.getTotalAmount(viewType)
                return totalExpenses
            }
        }
        return totalIncome
    }

    private var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null



    fun insertIncomeCategoryList(incomeCategoryEntity: List<IncomeCategoryEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            incomeCategoryRepo?.insertAllRecord(incomeCategoryEntity)
        }
    }

    fun getIncomeCategoryList(): LiveData<List<IncomeCategoryEntity>>? {
        incomeCategoryList = incomeCategoryRepo?.getIncomeCategory()
        return incomeCategoryList
    }


    fun insertCategory(incomeCategoryEntity: IncomeCategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            incomeCategoryRepo?.insertRecord(incomeCategoryEntity)
        }
    }

    var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null


    fun insertExpensesCategoryList(expensesCategoryEntityList: List<ExpensesCategoryEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            expensesCategoryRepo?.insertAllRecord(expensesCategoryEntityList)
        }
    }

    fun getExpensesCategoryAllList(): LiveData<List<ExpensesCategoryEntity>>? {

        expensesCategoryList = expensesCategoryRepo?.getExpensesCategory()

        return expensesCategoryList
    }


    fun insertCategory(expensesCategoryEntity: ExpensesCategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            expensesCategoryRepo?.insertRecord(expensesCategoryEntity)
        }
    }

    private var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null



    fun insertPaymentMethodList(paymentMethodList: List<PaymentMethodEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            paymentMethodRepo?.insertPaymentMethod(paymentMethodList)
        }

    }

    fun getAllPaymentMethodList(): LiveData<List<PaymentMethodEntity>>? {
        paymentMethodList = paymentMethodRepo?.getPaymentData()
        return paymentMethodList
    }
}
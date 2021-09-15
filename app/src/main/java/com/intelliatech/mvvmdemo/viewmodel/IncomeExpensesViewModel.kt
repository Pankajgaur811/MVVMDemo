package com.intelliatech.mvvmdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeExpensesViewModel(application: Application) : AndroidViewModel(application) {
    private var incomeExpensesRepo: IncomeExpensesRepo? = null
    var totalAmount: LiveData<Int>? = null
    var totalExpenses: LiveData<Int>? = null
    var totalIncome: LiveData<Int>? = null
    var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null


    init {
        val db = DatabaseHelper.initializeDB(application)
        incomeExpensesRepo = IncomeExpensesRepo(db.incomeExpensesDao())
    }

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
                totalExpenses =  incomeExpensesRepo?.getTotalAmount(viewType)
                return totalExpenses
            }
        }
        return totalIncome
    }



}
package com.intelliatech.mvvmdemo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepos
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity

class IncomeExpensesViewModel : ViewModel() {
    var totalAmount: LiveData<Int>? = null
    var totalExpenses: LiveData<Int>? = null
    var totalIncome: LiveData<Int>? = null

    var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null

    fun insertRecord(context: Context, incomeExpensesEntity: IncomeExpensesEntity) {
        IncomeExpensesRepos.insertRecord(context, incomeExpensesEntity)
    }

    fun updateRecord(context: Context, incomeExpensesEntity: IncomeExpensesEntity): Int {
        return IncomeExpensesRepos.updateRecord(context, incomeExpensesEntity)
    }

    fun getAllRecordList(context: Context, viewType: Int): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList = IncomeExpensesRepos.getIncomeExpeneseCategoryList(context, viewType)
        return incomeExpensesList
    }

    fun getDataBetweenTwoDates(
        context: Context,
        start_date: String,
        end_date: String,
        viewType: Int
    ): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList =
            IncomeExpensesRepos.getDataInBetweenDates(context, start_date, end_date, viewType)
        return incomeExpensesList

    }

    fun deleteRecord(context: Context, incomeExpensesEntity: IncomeExpensesEntity) {
        IncomeExpensesRepos.deleteRecord(context, incomeExpensesEntity)
    }

    fun getTotalAmount(context: Context, viewType: Int): LiveData<Int>? {

        when (viewType) {
            0 -> {
                totalIncome = IncomeExpensesRepos.getTotalAmount(context, viewType)
                return totalIncome
            }
            1 -> {
                totalExpenses = IncomeExpensesRepos.getTotalAmount(context, viewType)
                return totalExpenses
            }
        }
        return totalIncome
    }


}
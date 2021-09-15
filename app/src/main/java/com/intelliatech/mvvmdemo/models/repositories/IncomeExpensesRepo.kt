package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeExpensesDAO

class IncomeExpensesRepo(private val incomeExpensesDAO: IncomeExpensesDAO) {

    private var totalIncome: LiveData<Int>? = null
    private var totalExpenses: LiveData<Int>? = null
    var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null

    suspend fun insertRecord(incomeExpenses: IncomeExpensesEntity) {
        incomeExpensesDAO.addRecord(incomeExpenses)

    }

    fun getDataInBetweenDates(
        start_date: String,
        end_date: String,
        viewType: Int
    ): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList =
            incomeExpensesDAO.getDataBetweenTwoDates(start_date, end_date, viewType)
        return incomeExpensesList
    }


    suspend fun updateRecord(incomeExpenses: IncomeExpensesEntity): Int {
        var value = 0
        value = incomeExpensesDAO.updateRecord(incomeExpenses)

        return value
    }

    suspend fun deleteRecord(incomeExpenses: IncomeExpensesEntity) {
        incomeExpensesDAO.deleteRecord(incomeExpenses)

    }

    fun getIncomeExpensesRecordList(viewType: Int): LiveData<List<IncomeExpensesEntity>>? {

        incomeExpensesList = incomeExpensesDAO.getAllIncomeExpensesRecordList(viewType)
        return incomeExpensesList

    }

    fun getTotalAmount(
        viewType: Int
    ): LiveData<Int>? {
        when (viewType) {
            0 -> {
                totalIncome = incomeExpensesDAO.getTotalAmount(viewType)
                return totalIncome
            }
            1 -> {
                totalExpenses = incomeExpensesDAO.getTotalAmount(viewType)
                return totalExpenses
            }
        }

        return totalIncome
    }

}
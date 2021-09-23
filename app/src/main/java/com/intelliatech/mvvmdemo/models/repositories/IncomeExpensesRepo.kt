package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase

class IncomeExpensesRepo(private val db: IncomeExpensesDatabase) {

    private var totalIncome: LiveData<Int>? = null
    private var totalExpenses: LiveData<Int>? = null
    var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null

    suspend fun insertRecord(incomeExpenses: IncomeExpensesEntity) {
        db.incomeExpensesDao().addRecord(incomeExpenses)

    }

    fun getDataInBetweenDates(
        start_date: String,
        end_date: String,
        viewType: Int
    ): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList =
            db.incomeExpensesDao().getDataBetweenTwoDates(start_date, end_date, viewType)
        return incomeExpensesList
    }


    suspend fun updateRecord(incomeExpenses: IncomeExpensesEntity): Int {

        return  db.incomeExpensesDao().updateRecord(incomeExpenses)
    }

    suspend fun deleteRecord(incomeExpenses: IncomeExpensesEntity) {
        db.incomeExpensesDao().deleteRecord(incomeExpenses)
    }

    fun getIncomeExpensesRecordList(viewType: Int): LiveData<List<IncomeExpensesEntity>>? {
        incomeExpensesList = db.incomeExpensesDao().getAllIncomeExpensesRecordList(viewType)
        return incomeExpensesList
    }

    fun getTotalAmount(
        viewType: Int
    ): LiveData<Int>? {
        when (viewType) {
            0 -> {
                totalIncome = db.incomeExpensesDao().getTotalAmount(viewType)
                return totalIncome
            }
            1 -> {
                totalExpenses = db.incomeExpensesDao().getTotalAmount(viewType)
                return totalExpenses
            }
        }

        return totalIncome
    }

}
package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeExpensesDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class IncomeExpensesRepos(private val incomeExpensesDAO: IncomeExpensesDAO) {

    companion object {
        private var totalIncome: LiveData<Int>? = null
        private var totalExpenses: LiveData<Int>? = null
        var incomeExpensesList: LiveData<List<IncomeExpensesEntity>>? = null

        fun insertRecord(context: Context, incomeExpenses: IncomeExpensesEntity) {
            CoroutineScope(IO).launch {

                DatabaseHelper.initializeDB(context).incomeExpensesDao().addRecord(incomeExpenses)
            }
        }


        fun getDataInBetweenDates(
            context: Context,
            start_date: String,
            end_date: String,
            viewType: Int
        ): LiveData<List<IncomeExpensesEntity>>? {

            incomeExpensesList =
                DatabaseHelper.initializeDB(context).incomeExpensesDao()
                    .getDataBetweenTwoDates(start_date, end_date, viewType)
            return incomeExpensesList
        }


        fun updateRecord(context: Context, incomeExpenses: IncomeExpensesEntity): Int {
            var value = 0
            CoroutineScope(IO).launch {

                value = DatabaseHelper.initializeDB(context).incomeExpensesDao()
                    .updateRecord(incomeExpenses)
            }
            return value
        }

        fun deleteRecord(context: Context, incomeExpenses: IncomeExpensesEntity) {
            CoroutineScope(IO).launch {
                DatabaseHelper.initializeDB(context).incomeExpensesDao()
                    .deleteRecord(incomeExpenses)
            }

        }

        fun getIncomeExpeneseCategoryList(
            context: Context,
            viewType: Int
        ): LiveData<List<IncomeExpensesEntity>>? {

            incomeExpensesList = DatabaseHelper.initializeDB(context).incomeExpensesDao()
                .getAllIncomeExpensesRecordList(viewType)
            return incomeExpensesList

        }

        fun getTotalAmount(
            context: Context,
            viewType: Int
        ): LiveData<Int>? {
            when (viewType) {
                0 -> {
                    totalIncome = DatabaseHelper.initializeDB(context).incomeExpensesDao()
                        .getTotalAmount(viewType)
                    return totalIncome
                }
                1 -> {
                    totalExpenses = DatabaseHelper.initializeDB(context).incomeExpensesDao()
                        .getTotalAmount(viewType)
                    return totalExpenses
                }
            }

            return totalIncome
        }

    }


}
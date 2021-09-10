package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.ExpensesCategoryDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpensesCategoryRepo(private val expensesCategoryDao: ExpensesCategoryDAO) {

    companion object {

        var incomeExpensesDatabase: IncomeExpensesDatabase? = null

        fun initializeDB(context: Context): IncomeExpensesDatabase {
            return IncomeExpensesDatabase.getInstance(context)
        }

        var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null
        fun insertAllRecord(
            context: Context, expensesCategoryEntityList: List<ExpensesCategoryEntity>
        ) {
            incomeExpensesDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                incomeExpensesDatabase!!.expensesCategoryDao()
                    .addAllCategory(expensesCategoryEntityList)
            }
        }

        fun insertRecord(context: Context, expensesCategoryEntity: ExpensesCategoryEntity) {

            CoroutineScope(Dispatchers.IO).launch {
                incomeExpensesDatabase!!.expensesCategoryDao().addCategory(expensesCategoryEntity)
            }
        }

        fun getExpoensesCategoryList(
            context: Context
        ): LiveData<List<ExpensesCategoryEntity>>? {
            incomeExpensesDatabase = initializeDB(context)
            expensesCategoryList = incomeExpensesDatabase!!.expensesCategoryDao().getAllCategory()

            return expensesCategoryList
        }

    }
}
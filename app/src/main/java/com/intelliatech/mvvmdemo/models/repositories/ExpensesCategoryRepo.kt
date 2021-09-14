package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.ExpensesCategoryDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpensesCategoryRepo(private val expensesCategoryDao: ExpensesCategoryDAO) {

    companion object {



        var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null
        fun insertAllRecord(
            context: Context, expensesCategoryEntityList: List<ExpensesCategoryEntity>
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                DatabaseHelper.initializeDB(context)!!.expensesCategoryDao()
                    .addAllCategory(expensesCategoryEntityList)
            }
        }

        fun insertRecord(context: Context, expensesCategoryEntity: ExpensesCategoryEntity) {

            CoroutineScope(Dispatchers.IO).launch {
                DatabaseHelper.initializeDB(context)!!.expensesCategoryDao().addCategory(expensesCategoryEntity)
            }
        }

        fun getExpoensesCategoryList(
            context: Context
        ): LiveData<List<ExpensesCategoryEntity>>? {
            expensesCategoryList =   DatabaseHelper.initializeDB(context)!!.expensesCategoryDao().getAllCategory()

            return expensesCategoryList
        }

    }
}
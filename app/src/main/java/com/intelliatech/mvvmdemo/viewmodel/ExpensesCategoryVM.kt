package com.intelliatech.mvvmdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpensesCategoryVM(application: Application) : AndroidViewModel(application) {
    private var expensesCategoryRepo: ExpensesCategoryRepo? = null
    var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null


    init {
        val db = DatabaseHelper.initializeDB(application)
        expensesCategoryRepo = ExpensesCategoryRepo(db.expensesCategoryDao())
    }

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

}
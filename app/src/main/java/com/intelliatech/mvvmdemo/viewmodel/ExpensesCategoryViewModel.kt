package com.intelliatech.mvvmdemo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo.Companion.insertRecord
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity

class ExpensesCategoryViewModel : ViewModel() {
    var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null

    fun insertCategory(context: Context, expensesCategoryEntity: ExpensesCategoryEntity) {
        ExpensesCategoryRepo.insertRecord(context, expensesCategoryEntity)
    }

    fun insertAllData(
        context: Context, expensesCategoryEntityList
        : List<ExpensesCategoryEntity>
    ) {
        ExpensesCategoryRepo.insertAllRecord(context, expensesCategoryEntityList)
    }

    fun getExpensesCategoryListt(context: Context): LiveData<List<ExpensesCategoryEntity>>? {
        expensesCategoryList = ExpensesCategoryRepo.getExpoensesCategoryList(context)
        return expensesCategoryList
    }
}
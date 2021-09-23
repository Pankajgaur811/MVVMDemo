package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase

class ExpensesCategoryRepo(val db: IncomeExpensesDatabase) {


    var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null
    suspend fun insertAllRecord(
        expensesCategoryEntityList: List<ExpensesCategoryEntity>
    ) {
        db.expensesCategoryDao().addAllCategory(expensesCategoryEntityList)
    }


    suspend fun insertRecord(expensesCategoryEntity: ExpensesCategoryEntity) {
        db.expensesCategoryDao().addCategory(expensesCategoryEntity)

    }

    fun getExpensesCategory(): LiveData<List<ExpensesCategoryEntity>>? {
        expensesCategoryList = db.expensesCategoryDao().getAllCategory()

        return expensesCategoryList
    }


}
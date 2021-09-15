package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.ExpensesCategoryDAO

class ExpensesCategoryRepo(val expensesCategoryDao: ExpensesCategoryDAO) {


    var expensesCategoryList: LiveData<List<ExpensesCategoryEntity>>? = null
    suspend fun insertAllRecord(
        expensesCategoryEntityList: List<ExpensesCategoryEntity>
    ) {
        expensesCategoryDao.addAllCategory(expensesCategoryEntityList)
    }


    suspend fun insertRecord(expensesCategoryEntity: ExpensesCategoryEntity) {
        expensesCategoryDao.addCategory(expensesCategoryEntity)

    }

    fun getExpensesCategory(): LiveData<List<ExpensesCategoryEntity>>? {
        expensesCategoryList =
            expensesCategoryDao.getAllCategory()

        return expensesCategoryList
    }


}
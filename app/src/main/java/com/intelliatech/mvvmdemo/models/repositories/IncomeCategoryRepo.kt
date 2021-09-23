package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase

class IncomeCategoryRepo(val db: IncomeExpensesDatabase) {

    var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null
    suspend fun insertAllRecord(incomeCategoryList: List<IncomeCategoryEntity>) {


        db.incomeCategoryDao().addAllCategory(incomeCategoryList)
    }

    suspend fun insertRecord(incomeCategory: IncomeCategoryEntity) {

        db.incomeCategoryDao().addCategory(incomeCategory)

    }

    fun getIncomeCategory(): LiveData<List<IncomeCategoryEntity>>? {
        incomeCategoryList = db.incomeCategoryDao().getAllCategory()

        return incomeCategoryList
    }
}
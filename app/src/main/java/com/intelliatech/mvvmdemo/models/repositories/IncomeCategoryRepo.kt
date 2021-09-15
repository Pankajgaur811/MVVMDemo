package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeCategoryDAO

class IncomeCategoryRepo(val incomeCategoryDAO: IncomeCategoryDAO) {

    var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null
    suspend fun insertAllRecord(incomeCategoryList: List<IncomeCategoryEntity>) {
        incomeCategoryDAO.addAllCategory(incomeCategoryList)
    }

    suspend fun insertRecord(incomeCategory: IncomeCategoryEntity) {
        incomeCategoryDAO.addCategory(incomeCategory)

    }

    fun getIncomeCategory(): LiveData<List<IncomeCategoryEntity>>? {
        incomeCategoryList = incomeCategoryDAO.getAllCategory()

        return incomeCategoryList
    }


}
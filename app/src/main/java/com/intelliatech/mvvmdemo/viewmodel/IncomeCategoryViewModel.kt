package com.intelliatech.mvvmdemo.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeCategoryDAO

class IncomeCategoryViewModel : ViewModel() {

    var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null

    fun insertCategory(context: Context, incomeCategoryEntity: IncomeCategoryEntity) {
        IncomeCategoryRepo.insertRecord(context, incomeCategoryEntity)
    }

    fun insertAllData(context: Context, incomeCategoryEntityList: List<IncomeCategoryEntity>) {
        IncomeCategoryRepo.insertAllRecord(context, incomeCategoryEntityList)
    }

    fun getIncomeCategoryList(
        context: Context
    ): LiveData<List<IncomeCategoryEntity>>? {
        incomeCategoryList = IncomeCategoryRepo.getIncomeCategoryList(context)
        return incomeCategoryList
    }
}
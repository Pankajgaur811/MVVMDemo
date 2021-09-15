package com.intelliatech.mvvmdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IncomeCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null
    private var incomeCategoryRepo: IncomeCategoryRepo? = null

    init {
        val db = DatabaseHelper.initializeDB(application)
        incomeCategoryRepo = IncomeCategoryRepo(db.incomeCategoryDao())
    }

    fun insertIncomeCategoryList(incomeCategoryEntity: List<IncomeCategoryEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            incomeCategoryRepo?.insertAllRecord(incomeCategoryEntity)
        }
    }

    fun getIncomeCategoryList(): LiveData<List<IncomeCategoryEntity>>? {
            incomeCategoryList = incomeCategoryRepo?.getIncomeCategory()
        return incomeCategoryList
    }


    fun insertCategory(incomeCategoryEntity: IncomeCategoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            incomeCategoryRepo?.insertRecord(incomeCategoryEntity)
        }
    }


}



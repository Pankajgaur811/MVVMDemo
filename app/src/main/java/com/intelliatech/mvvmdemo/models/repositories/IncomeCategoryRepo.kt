package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class IncomeCategoryRepo() {
    companion object {
        var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null
        fun insertAllRecord(
            context: Context,
            incomeCategoryList: List<IncomeCategoryEntity>
        ) {

            CoroutineScope(IO).launch {
                DatabaseHelper.initializeDB(context)!!.incomeCategoryDao()
                    .addAllCategory(incomeCategoryList)
            }
        }

        fun insertRecord(context: Context, incomeCategory: IncomeCategoryEntity) {

            CoroutineScope(IO).launch {
                DatabaseHelper.initializeDB(context)!!.incomeCategoryDao()
                    .addCategory(incomeCategory)
            }
        }

        fun getIncomeCategoryList(
            context: Context
        ): LiveData<List<IncomeCategoryEntity>>? {

            incomeCategoryList =
                DatabaseHelper.initializeDB(context)!!.incomeCategoryDao().getAllCategory()

            return incomeCategoryList
        }

    }
}
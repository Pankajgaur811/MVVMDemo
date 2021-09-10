package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeCategoryDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class IncomeCategoryRepo() {
    companion object {

        var incomeExpensesDatabase: IncomeExpensesDatabase? = null

        fun initializeDB(context: Context): IncomeExpensesDatabase {
            return IncomeExpensesDatabase.getInstance(context)
        }

        var incomeCategoryList: LiveData<List<IncomeCategoryEntity>>? = null
        fun insertAllRecord(
            context: Context,
            incomeCategoryList: List<IncomeCategoryEntity>
        ) {
            incomeExpensesDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                incomeExpensesDatabase!!.incomeCategoryDao().addAllCategory(incomeCategoryList)
            }
        }

        fun insertRecord(context: Context, incomeCategory: IncomeCategoryEntity) {

            CoroutineScope(IO).launch {
                incomeExpensesDatabase!!.incomeCategoryDao().addCategory(incomeCategory)
            }
        }

        fun getIncomeCategoryList(
            context: Context
        ): LiveData<List<IncomeCategoryEntity>>? {
            incomeExpensesDatabase = initializeDB(context)
           incomeCategoryList = incomeExpensesDatabase!!.incomeCategoryDao().getAllCategory()

            return incomeCategoryList
        }

    }
}
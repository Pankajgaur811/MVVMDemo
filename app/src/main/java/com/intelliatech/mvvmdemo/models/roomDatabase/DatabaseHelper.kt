package com.intelliatech.mvvmdemo.models.roomDatabase

import android.content.Context

class DatabaseHelper {

    companion object {

        fun initializeDB(context: Context): IncomeExpensesDatabase {
            return IncomeExpensesDatabase.getInstance(context)
        }
    }
}
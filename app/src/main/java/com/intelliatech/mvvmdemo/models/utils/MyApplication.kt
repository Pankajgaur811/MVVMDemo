package com.intelliatech.mvvmdemo.models.utils

import android.app.Application
import com.intelliatech.mvvmdemo.BuildConfig
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public class MyApplication : Application() {

    private var applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { IncomeExpensesDatabase.getInstance(this, applicationScope) }
    val incomeRepo by lazy { IncomeCategoryRepo(database.incomeCategoryDao()) }
    val incomeExpenseRepo by lazy { IncomeExpensesRepo(database.incomeExpensesDao()) }
    val expensesRepo by lazy { ExpensesCategoryRepo(database.expensesCategoryDao()) }
    val paymentRepo by lazy { PaymentMethodRepo(database.paymentMethodDao()) }


    override fun onCreate() {
        super.onCreate()
        instance = this
        // No need to cancel this scope as it'll be torn down with the process
      }

    companion object {
        private var instance: MyApplication? = null


        public fun getAppInstance(): MyApplication? {
            return instance
        }

    }
}
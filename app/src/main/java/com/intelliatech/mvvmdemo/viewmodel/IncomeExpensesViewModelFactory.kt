package com.intelliatech.mvvmdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo

class IncomeExpensesViewModelFactory(
    val incomeCategoryRepo: IncomeCategoryRepo?,
    val expensesCategoryRepo: ExpensesCategoryRepo?,
    val paymentMethodRepo: PaymentMethodRepo?,
    val incomeExpensesRepo: IncomeExpensesRepo?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(IncomeExpensesViewModel::class.java)) {

            return IncomeExpensesViewModel(
                incomeCategoryRepo,
                expensesCategoryRepo,
                paymentMethodRepo,
                incomeExpensesRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
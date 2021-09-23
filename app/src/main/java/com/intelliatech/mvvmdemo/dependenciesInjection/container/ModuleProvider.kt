package com.intelliatech.mvvmdemo.dependenciesInjection.container

import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.utils.UtilityHelper
import com.intelliatech.mvvmdemo.viewmodel.IncomeExpensesViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ModuleProvider : KoinComponent {
    //lazily intialize (when it is call then intialiaze object ot)
    val utilityHelper: UtilityHelper by inject()


    //eagerly intialize
//    private val utilityHelper1: UtilityHelper =get()

    val incomeExpensesVM: IncomeExpensesViewModel by inject()

    val incomeExpensesRepo: IncomeExpensesRepo by inject()
    val incomeCategoryRepo: IncomeCategoryRepo by inject()
    val expensesRepo: ExpensesCategoryRepo by inject()
    val paymentRepo: PaymentMethodRepo by inject()
    val db: IncomeExpensesDatabase by inject()
}
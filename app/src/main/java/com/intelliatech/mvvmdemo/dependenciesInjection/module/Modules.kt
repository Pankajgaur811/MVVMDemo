package com.intelliatech.mvvmdemo.dependenciesInjection.module

import com.intelliatech.mvvmdemo.models.repositories.ExpensesCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeCategoryRepo
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepo
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase
import com.intelliatech.mvvmdemo.models.utils.UtilityHelper
import com.intelliatech.mvvmdemo.viewmodel.IncomeExpensesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dBModule = module {
    val applicationScope = CoroutineScope(SupervisorJob())
    single { IncomeExpensesDatabase.getInstance(androidContext(), applicationScope) }
}

val incomeRepoModule = module { single { IncomeCategoryRepo(get()) } }

val expenseRepoModule = module { single { ExpensesCategoryRepo(get()) } }

val incomeExpenseRepoModule = module { single { IncomeExpensesRepo(get()) } }

val paymentRepoModule = module { single { PaymentMethodRepo(get()) } }

val utilityHelper = module { single { UtilityHelper() } }

val incomeVModule = module { viewModel { IncomeExpensesViewModel(get(), get(), get(), get()) } }
package com.intelliatech.mvvmdemo.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliatech.mvvmdemo.models.repositories.IncomeExpensesRepos

class IncomeExpensesViewModel(private val incomeExpensesRepo: IncomeExpensesRepos) : ViewModel() {
    val amount = MutableLiveData<String>()
    val payee = MutableLiveData<String>()
}
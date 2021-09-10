package com.intelliatech.mvvmdemo.models.repositories

import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeCategoryDAO
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.IncomeExpensesDAO

class IncomeExpensesRepos(private val incomeExpensesDAO :IncomeExpensesDAO) {

    val incomeExpensesList = incomeExpensesDAO.getAllIncomeExpensesRecordList()


    suspend fun insertRecord(incomeExpenses : IncomeExpensesEntity)
    {
        incomeExpensesDAO.addRecord(incomeExpenses)
    } suspend fun updateRecord(incomeExpenses : IncomeExpensesEntity)
    {
        incomeExpensesDAO.updateRecord(incomeExpenses)
    }
}
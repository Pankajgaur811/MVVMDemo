package com.intelliatech.mvvmdemo.models.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity

@Dao
interface IncomeExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(incomeExpenses: IncomeExpensesEntity)

    @Update
    suspend fun updateRecord(incomeExpenses: IncomeExpensesEntity)

    @Delete
    suspend fun deleteRecord(incomeExpenses: IncomeExpensesEntity)

    @Query("SELECT * FROM Income_Expenses_Record WHERE type =type")
    fun getAllIncomeExpensesRecordList(): LiveData<List<IncomeExpensesEntity>>

}
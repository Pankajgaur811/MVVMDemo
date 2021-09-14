package com.intelliatech.mvvmdemo.models.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity

@Dao
interface IncomeExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(incomeExpenses: IncomeExpensesEntity)

    @Update
    suspend fun updateRecord(incomeExpenses: IncomeExpensesEntity): Int

    @Delete
    suspend fun deleteRecord(incomeExpenses: IncomeExpensesEntity)

    @Query("SELECT * FROM Income_Expenses_Record WHERE type=:type")
    fun getAllIncomeExpensesRecordList(type: Int): LiveData<List<IncomeExpensesEntity>>

    @Query("SELECT sum(Amount) FROM Income_Expenses_Record where type =:type")
    fun getTotalAmount(type: Int): LiveData<Int>

    @Query("SELECT * FROM INCOME_EXPENSES_RECORD  where type=:type AND Date  between :start_date AND :end_date ")
    fun getDataBetweenTwoDates(
        start_date: String,
        end_date: String, type: Int
    ): LiveData<List<IncomeExpensesEntity>>
}
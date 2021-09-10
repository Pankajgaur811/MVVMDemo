package com.intelliatech.mvvmdemo.models.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.ExpensesCategoryEntity

@Dao
interface ExpensesCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(expensesCategory: ExpensesCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllCategory(expensesCategoryList: List<ExpensesCategoryEntity>)

    @Query("select * from category_expenses order By id ASC")
    fun getAllCategory(): LiveData<List<ExpensesCategoryEntity>>
}
package com.intelliatech.mvvmdemo.models.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeCategoryEntity

@Dao
interface IncomeCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(incomeCategory: IncomeCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllCategory(incomeCategoryList: List<IncomeCategoryEntity>)

    @Query("select * from Category_Income order By id ASC")
    fun getAllCategory(): LiveData<List<IncomeCategoryEntity>>
}
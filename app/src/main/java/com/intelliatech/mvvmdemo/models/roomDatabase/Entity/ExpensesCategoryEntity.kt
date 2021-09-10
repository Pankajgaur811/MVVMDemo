package com.intelliatech.mvvmdemo.models.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category_Expenses")
data class ExpensesCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val category_name: String
)
package com.intelliatech.mvvmdemo.models.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Income_Expenses_Record")
data class IncomeExpensesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val amount: Int,
    val payer: String,
    val category: String,
    val payment_method: String,
    val date: String,
    val time: String,
    val description: String,
    val type: Int
)

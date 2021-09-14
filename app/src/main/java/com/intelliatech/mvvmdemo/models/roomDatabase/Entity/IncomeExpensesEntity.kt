package com.intelliatech.mvvmdemo.models.roomDatabase.Entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Income_Expenses_Record")
@Parcelize
data class IncomeExpensesEntity(
    var amount: Int,
    var payer: String,
    var category: String,
    var payment_method: String,
    var date: String,
    var time: String,
    var description: String,
    var type: Int
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}

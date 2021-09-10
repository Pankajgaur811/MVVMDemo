package com.intelliatech.mvvmdemo.models.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PaymentMethod")
class PaymentMethodEntity(@PrimaryKey(autoGenerate = true) val id: Int, val paymentMethodName: String)
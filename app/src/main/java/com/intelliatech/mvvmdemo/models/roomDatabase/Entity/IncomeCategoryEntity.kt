package com.intelliatech.mvvmdemo.models.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Category_Income")
data class IncomeCategoryEntity (@PrimaryKey(autoGenerate = false) val id: Int,val categotry_name : String)
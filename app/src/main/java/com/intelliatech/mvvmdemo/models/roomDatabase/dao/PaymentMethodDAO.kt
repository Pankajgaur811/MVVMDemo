package com.intelliatech.mvvmdemo.models.roomDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity

@Dao
interface PaymentMethodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPaymentMethod(paymentMethodEntityList: List<PaymentMethodEntity>)

    @Query("SELECT * FROM PaymentMethod ")
    fun getAllPaymentMethod(): LiveData<List<PaymentMethodEntity>>

}
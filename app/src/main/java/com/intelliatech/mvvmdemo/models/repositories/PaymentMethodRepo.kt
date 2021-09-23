package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.IncomeExpensesDatabase

class PaymentMethodRepo(private val db: IncomeExpensesDatabase) {


    var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null

    suspend fun insertPaymentMethod(
        paymentMethodList: List<PaymentMethodEntity>
    ) {
        db.paymentMethodDao().insertAllPaymentMethod(paymentMethodList)
    }


    fun getPaymentData(): LiveData<List<PaymentMethodEntity>>? {
        paymentMethodList = db.paymentMethodDao().getAllPaymentMethod()
        return paymentMethodList
    }

}
package com.intelliatech.mvvmdemo.models.repositories

import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import com.intelliatech.mvvmdemo.models.roomDatabase.dao.PaymentMethodDAO

class PaymentMethodRepo(private val paymentMethodDAO: PaymentMethodDAO) {


    var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null

    suspend fun insertPaymentMethod(
        paymentMethodList: List<PaymentMethodEntity>
    ) {
        paymentMethodDAO.insertAllPaymentMethod(paymentMethodList)
    }


    fun getPaymentData(): LiveData<List<PaymentMethodEntity>>? {
        paymentMethodList = paymentMethodDAO.getAllPaymentMethod()
        return paymentMethodList
    }

}
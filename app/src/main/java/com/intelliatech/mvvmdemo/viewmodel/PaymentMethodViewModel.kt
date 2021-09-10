package com.intelliatech.mvvmdemo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity

class PaymentMethodViewModel : ViewModel() {

     var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null


    fun insertPaymentMethodList(context: Context, paymentMethodList: List<PaymentMethodEntity>) {
        PaymentMethodRepo.insertPaymentMethod(context, paymentMethodList)

    }

    fun getAllPaymentMethodList(context: Context): LiveData<List<PaymentMethodEntity>>? {
        paymentMethodList = PaymentMethodRepo.getPaymentMethodList(context)
        return paymentMethodList
    }
}
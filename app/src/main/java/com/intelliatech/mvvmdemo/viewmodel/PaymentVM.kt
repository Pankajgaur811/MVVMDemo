package com.intelliatech.mvvmdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.intelliatech.mvvmdemo.models.repositories.PaymentMethodRepo
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentVM(application: Application) : AndroidViewModel(application) {

    private var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null
    var paymentMethodRepo: PaymentMethodRepo? = null

    init {
        val db = DatabaseHelper.initializeDB(application)
        paymentMethodRepo = PaymentMethodRepo(db.paymentMethodDao())

    }

    fun insertPaymentMethodList(paymentMethodList: List<PaymentMethodEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            paymentMethodRepo?.insertPaymentMethod(paymentMethodList)
        }

    }

    fun getAllPaymentMethodList(): LiveData<List<PaymentMethodEntity>>? {
        paymentMethodList = paymentMethodRepo?.getPaymentData()
        return paymentMethodList
    }
}
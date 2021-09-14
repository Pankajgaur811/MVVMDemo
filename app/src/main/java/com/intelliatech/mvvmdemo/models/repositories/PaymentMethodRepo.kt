package com.intelliatech.mvvmdemo.models.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.intelliatech.mvvmdemo.models.roomDatabase.DatabaseHelper
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.PaymentMethodEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PaymentMethodRepo {

    companion object {

        var paymentMethodList: LiveData<List<PaymentMethodEntity>>? = null

        fun insertPaymentMethod(context: Context, paymentMethodList: List<PaymentMethodEntity>) {

            CoroutineScope(IO).launch {
                DatabaseHelper.initializeDB(context)!!.paymentMethodDao()
                    .insertAllPaymentMethod(paymentMethodList)
            }
        }


        fun getPaymentMethodList(context: Context): LiveData<List<PaymentMethodEntity>>? {
            paymentMethodList =
                DatabaseHelper.initializeDB(context)!!.paymentMethodDao().getAllPaymentMethod()
            return paymentMethodList
        }
    }
}
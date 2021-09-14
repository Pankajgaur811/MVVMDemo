package com.intelliatech.mvvmdemo.views.clickEventListeners

import android.view.View
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity

interface SingleClickEventList {


    fun clickSingleRecordList(incomeExpensesEntity: IncomeExpensesEntity, view: View)
}
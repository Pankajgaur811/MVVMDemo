package com.intelliatech.mvvmdemo.views.dataAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intelliatech.mvvmdemo.databinding.SingleIncomeExpensesCardLayoutBinding
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.models.utils.UtilityHelper
import com.intelliatech.mvvmdemo.views.clickEventListeners.SingleClickEventList

class IncomeExpensesListDataAdapter(
    context: Context,
    var incomeExpensesEntityList: List<IncomeExpensesEntity>,
    var singleClickEventList: SingleClickEventList
) : RecyclerView.Adapter<IncomeExpensesListDataAdapter.IncomeExpensesListVH>() {
    lateinit var binding: SingleIncomeExpensesCardLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeExpensesListVH {

        binding = SingleIncomeExpensesCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncomeExpensesListVH(binding)
    }

    override fun onBindViewHolder(holder: IncomeExpensesListVH, position: Int) {
        holder.binding.tvAmount.text = incomeExpensesEntityList.get(position).amount.toString()
        var date = UtilityHelper.convertMiliSecondToDate(
            "dd-MM-yyy",
            incomeExpensesEntityList.get(position).date.toLong()
        )
        holder.binding.tvDate.text = date
        holder.binding.tvDescription.text = incomeExpensesEntityList.get(position).description
        holder.binding.tvTime.text = incomeExpensesEntityList.get(position).time
        holder.binding.tvSerialNo.text = (position + 1).toString()
        holder.binding.cvTopView.setOnClickListener {
            singleClickEventList.clickSingleRecordList(
                incomeExpensesEntityList.get(position),
                binding.root
            )
        }
    }

    override fun getItemCount(): Int {
        return incomeExpensesEntityList.size
    }

    class IncomeExpensesListVH(binding: SingleIncomeExpensesCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: SingleIncomeExpensesCardLayoutBinding = binding
    }
}
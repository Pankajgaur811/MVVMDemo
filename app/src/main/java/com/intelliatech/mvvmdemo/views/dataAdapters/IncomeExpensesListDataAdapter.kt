package com.intelliatech.mvvmdemo.views.dataAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intelliatech.mvvmdemo.databinding.SingleIncomeExpensesCardLayoutBinding
import com.intelliatech.mvvmdemo.dependenciesInjection.container.ModuleProvider
import com.intelliatech.mvvmdemo.models.roomDatabase.Entity.IncomeExpensesEntity
import com.intelliatech.mvvmdemo.views.clickEventListeners.SingleClickEventList
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class IncomeExpensesListDataAdapter(
    private var incomeExpensesEntityList: List<IncomeExpensesEntity>,
    private val singleClickEventList: SingleClickEventList
) : RecyclerView.Adapter<IncomeExpensesListDataAdapter.IncomeExpensesListVH>() {
    private lateinit var binding: SingleIncomeExpensesCardLayoutBinding
    private val component = ModuleProvider()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeExpensesListVH {

        binding = SingleIncomeExpensesCardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncomeExpensesListVH(binding)
    }

    @KoinApiExtension
    override fun onBindViewHolder(holder: IncomeExpensesListVH, position: Int) {
        holder.binding.tvAmount.text = incomeExpensesEntityList.get(position).amount.toString()
        val date = component.utilityHelper.convertMiliSecondToDate(
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

    class IncomeExpensesListVH(var binding: SingleIncomeExpensesCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
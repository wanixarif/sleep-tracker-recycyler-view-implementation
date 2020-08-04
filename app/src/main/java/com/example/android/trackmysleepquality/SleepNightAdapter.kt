package com.example.android.trackmysleepquality

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightAdapter() :ListAdapter<SleepNight,MyViewHolder>(SleepNightDiffUtilCallback()) {
//Old
//class SleepNightAdapter() :RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item)
    }




}

class MyViewHolder private  constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {


    fun bind(item: SleepNight) {
        val res=itemView.context.resources
        binding.sleepTime.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        binding.sleepQualityText.text = convertNumericQualityToString(item.sleepQuality, res)
        binding.sleepQualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
        )
    }

    companion object {
        fun from(parent: ViewGroup): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
            return MyViewHolder(binding)
        }
    }

}



class SleepNightDiffUtilCallback:DiffUtil.ItemCallback<SleepNight>(){
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return  newItem.nightId==oldItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return  oldItem==newItem
    }
}
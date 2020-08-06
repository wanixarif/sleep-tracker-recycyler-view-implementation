package com.example.android.trackmysleepquality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1


class SleepNightAdapter(val clickListener: SleepListener) :ListAdapter<DataItem,RecyclerView.ViewHolder>(SleepNightDiffUtilCallback()) {
//Old
//class SleepNightAdapter() :RecyclerView.Adapter<MyViewHolder>() {



    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder.from(parent)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> {
                MyViewHolder.from(parent)
            }
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }



//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item=getItem(position)
//        holder.bind(getItem(position)!!,clickListener)
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }


}

class MyViewHolder private  constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {


    fun bind(item: SleepNight, clickListener: SleepListener) {

        binding.sleepNightVar=item
        binding.clickListener=clickListener
        binding.executePendingBindings()

        //Bind utils replaced everything below

//        val res=itemView.context.resources
//        binding.sleepTime.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//        binding.sleepQualityText.text = convertNumericQualityToString(item.sleepQuality, res)
//        binding.sleepQualityImage.setImageResource(
//                when (item.sleepQuality) {
//                    0 -> R.drawable.ic_sleep_0
//                    1 -> R.drawable.ic_sleep_1
//                    2 -> R.drawable.ic_sleep_2
//                    3 -> R.drawable.ic_sleep_3
//                    4 -> R.drawable.ic_sleep_4
//                    5 -> R.drawable.ic_sleep_5
//                    else -> R.drawable.ic_sleep_active
//                }
//        )
    }

    companion object {
        fun from(parent: ViewGroup): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
            return MyViewHolder(binding)
        }
    }

}



class SleepNightDiffUtilCallback:DiffUtil.ItemCallback<DataItem>(){
//    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
//        return  newItem.nightId==oldItem.nightId
//    }
//
//    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
//        return  oldItem==newItem
//    }

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem==newItem
    }
}


class SleepListener(val clickListener: (sleepNightId:Long)->Unit){
    fun onClick(night:SleepNight)=clickListener(night.nightId)
}



sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

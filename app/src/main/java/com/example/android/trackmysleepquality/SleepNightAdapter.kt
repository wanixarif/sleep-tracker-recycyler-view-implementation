package com.example.android.trackmysleepquality

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter() :RecyclerView.Adapter<MyViewHolder>() {

    var data= listOf<SleepNight>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view =layoutInflater.inflate(R.layout.list_item_sleep_night,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=data[position]
        holder.bind(item)
    }


}

class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

    val sleepLength=itemView.findViewById<TextView>(R.id.sleepTime)
    val sleepQuality=itemView.findViewById<TextView>(R.id.sleepQualityText)
    val imageSleepQuality=itemView.findViewById<ImageView>(R.id.sleepQualityImage)


    fun bind(item: SleepNight) {
        val res=itemView.context.resources
        sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        sleepQuality.text = convertNumericQualityToString(item.sleepQuality, res)
        imageSleepQuality.setImageResource(
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
}
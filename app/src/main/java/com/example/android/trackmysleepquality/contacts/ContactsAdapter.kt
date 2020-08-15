package com.example.android.trackmysleepquality.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.TestData
import com.example.android.trackmysleepquality.databinding.ListItemContactBinding

class ContactsAdapter(val clickListener: ContactClickListener) :androidx.recyclerview.widget.ListAdapter<TestData,ContactsViewHolder>(ContactsDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)


    }

}

class ContactsViewHolder private constructor(val binding: ListItemContactBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(item: TestData, clickListener: ContactClickListener) {
        binding.contactListItem=item
        binding.clickListener=clickListener
        binding.executePendingBindings()

//        name.text = item.name
//        num.text = item.num.toString()
    }

    companion object{
        fun from(parent: ViewGroup): ContactsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_contact, parent, false)
            val binding=ListItemContactBinding.inflate(layoutInflater,parent,false)
            return ContactsViewHolder(binding)
        }

    }
//
//    var name:TextView= binding.name
//    var num:TextView= binding.number
}


class ContactsDiffCallback:DiffUtil.ItemCallback<TestData>(){
    override fun areItemsTheSame(oldItem: TestData, newItem: TestData): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TestData, newItem: TestData): Boolean {
        return  oldItem == newItem
    }

}


class ContactClickListener(val clickListener:(id:Long)->Unit){
    fun onClick(contact:TestData)=clickListener(contact.id)
}
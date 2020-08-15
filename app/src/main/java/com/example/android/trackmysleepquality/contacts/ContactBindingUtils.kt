package com.example.android.trackmysleepquality.contacts

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.database.TestData

@BindingAdapter("setContactName")
fun TextView.setContactName(item:TestData?){
    text=item?.name
}

@BindingAdapter("setContactNumber")
fun TextView.setContactNumbrr(item:TestData?){
    text=item?.num.toString()
}
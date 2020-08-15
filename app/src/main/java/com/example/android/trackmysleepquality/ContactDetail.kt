package com.example.android.trackmysleepquality

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.contacts.TestDirections
import com.example.android.trackmysleepquality.database.TestDatabase
import com.example.android.trackmysleepquality.databinding.ContactDetailFragmentBinding


class ContactDetail : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding:ContactDetailFragmentBinding=DataBindingUtil.inflate(layoutInflater,R.layout.contact_detail_fragment,container,false)


        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.

        val dataSource = TestDatabase.getInstance(application).testDatabaseDao

        val args=ContactDetailArgs.fromBundle(arguments!!)

        val viewModelFactory=ContactDetailViewModelFactory(args.id,dataSource)
        val viewmodel=ViewModelProviders.of(this,viewModelFactory).get(ContactDetailViewModel::class.java)
        binding.contact=viewmodel

        //Fuckin bitch wasted 2 days of coding cuz I missed it somehow
        binding.setLifecycleOwner(this)


        viewmodel.delete.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController().navigate(ContactDetailDirections.actionContactDetailToTest())
                viewmodel.deleteClicked()
            }
        })

        binding.edit.setOnClickListener {
            Toast.makeText(context,args.id.toString(),Toast.LENGTH_SHORT).show()
        }

        return  binding.root
    }



}

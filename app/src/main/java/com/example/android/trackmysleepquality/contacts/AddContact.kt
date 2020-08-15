package com.example.android.trackmysleepquality.contacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.TestDatabase
import com.example.android.trackmysleepquality.databinding.FragmentAddContactBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddContact : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
            val binding:FragmentAddContactBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_add_contact,container,false)

            val application = requireNotNull(this.activity).application

            // Create an instance of the ViewModel Factory.
            val dataSource = TestDatabase.getInstance(application).testDatabaseDao
            val viewModelFactory=AddContactViewModelFactory(dataSource)


            val viewModel=ViewModelProviders.of(this,viewModelFactory).get(AddContactViewModel::class.java)
            binding.addContactViewModel=viewModel

            viewModel.saveClicked.observe(viewLifecycleOwner, Observer { clicked->
                if (clicked and (binding.number.text.toString()!="")){
                    viewModel.addContact(binding.name.text.toString(),binding.number.text.toString().toLong())
                    viewModel.saved()
                }
            })



            return binding.root
    }




}

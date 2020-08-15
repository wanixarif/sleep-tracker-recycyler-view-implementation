package com.example.android.trackmysleepquality.contacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.TestData
import com.example.android.trackmysleepquality.database.TestDatabase
import com.example.android.trackmysleepquality.databinding.FragmentTestBinding
import com.example.android.trackmysleepquality.sleepquality.SleepQualityFragmentArgs
import com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModelFactory
import kotlinx.android.synthetic.main.fragment_test.*

class Test : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding:FragmentTestBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_test,container,false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = TestDatabase.getInstance(application).testDatabaseDao
        val viewModelFactory=ContactsViewModelFactory(dataSource)

        val viewModel=ViewModelProviders.of(this,viewModelFactory).get(ContactsViewModel::class.java)

        binding.testViewModel=viewModel



        binding.lifecycleOwner = this
        viewModel.navigateToAddContact.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(TestDirections.actionTestToAddContact())
                viewModel.navToAddContactNavigated()
            }
        })

        val adapter=ContactsAdapter(ContactClickListener{id->
            this.findNavController().navigate(TestDirections.actionTestToContactDetail(id))
        })
        binding.contactsRecyclerView.adapter=adapter
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

}

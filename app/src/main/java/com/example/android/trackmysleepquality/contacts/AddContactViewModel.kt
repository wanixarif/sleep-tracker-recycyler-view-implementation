package com.example.android.trackmysleepquality.contacts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.TestData
import com.example.android.trackmysleepquality.database.TestDatabaseDao
import kotlinx.coroutines.*

class AddContactViewModel (val database:TestDatabaseDao):ViewModel(){

    private var viewModelJob=Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private  val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)


    private var _saveClicked=MutableLiveData<Boolean>()
    val saveClicked:LiveData<Boolean>
        get() = _saveClicked


    fun saveClicked(){
        _saveClicked.value=true
    }
    fun saved(){
        _saveClicked.value=false
    }


    fun addContact(name:String,num:Long) {
        uiScope.launch {
            val newContact= TestData()
            newContact.name=name
            newContact.num=num
            addContactToDB(newContact)
        }
    }

    private suspend fun addContactToDB(contact:TestData){
        withContext(Dispatchers.IO) {
            database.insert(contact)
            Log.e("alpha","Inserted")
        }
    }


}
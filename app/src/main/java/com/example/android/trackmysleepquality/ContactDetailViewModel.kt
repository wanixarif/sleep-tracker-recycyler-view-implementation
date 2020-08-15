package com.example.android.trackmysleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.TestData
import com.example.android.trackmysleepquality.database.TestDatabaseDao
import kotlinx.coroutines.*

class ContactDetailViewModel(private val id:Long=0L,dataSource:TestDatabaseDao) : ViewModel() {



    val database=dataSource


    val viewModelJob= Job()
    val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    private var _delete=MutableLiveData<Boolean>()
    val delete:LiveData<Boolean>
        get() = _delete


    private val contact = MediatorLiveData<TestData>()
    fun fetchContact() = contact

    init {

        contact.addSource(database.getContact(id),contact::setValue)
    }



    fun deleteClicked(){
        _delete.value=false
    }

    fun delete(){
        uiScope.launch {
            _delete.value=true
            deleteFromdb(id)

        }
    }
    private suspend fun deleteFromdb(contactId:Long){
        withContext(Dispatchers.IO){
            database.delete(contactId)

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

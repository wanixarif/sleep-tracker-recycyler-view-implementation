package com.example.android.trackmysleepquality.contacts

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.TestData
import com.example.android.trackmysleepquality.database.TestDatabaseDao
import kotlinx.coroutines.*

class ContactsViewModel(val database:TestDatabaseDao) :ViewModel(){


    private val viewModelJob= Job()
    private val uiScope= CoroutineScope(Dispatchers.Main + viewModelJob)

    val contacts=database.allContacts()

//    val contactsString=Transformations.map(contacts) {
//            formatContacts(it)
//    }
//    fun formatContacts(contacts: List<TestData>): Spanned {
//        val sb = StringBuilder()
//        sb.apply {
//            contacts.forEach {
//                append("<br>")
//                append(it.name)
//                append(" : ")
//                append(it.num)
//                append("<br>")
//
//            }
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
//        } else {
//            return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
//        }
//    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var _navigateToAddContact=MutableLiveData<Boolean>()


    val navigateToAddContact:LiveData<Boolean>
        get() = _navigateToAddContact

    fun navToAddContact(){
        _navigateToAddContact.value=true
    }
    fun navToAddContactNavigated(){
        _navigateToAddContact.value=false
    }

    fun clear(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                return@withContext database.clear()
            }
        }
    }

}
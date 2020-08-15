package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TestDatabaseDao{

    @Insert
    fun insert(contact:TestData)

    @Update
    fun update(contact: TestData)

    @Query("select * from test_data where id =:id")
    fun getContact(id:Long):LiveData<TestData>

    @Query("delete from test_data")
    fun clear()

    @Query("delete from test_data where id=:id")
    fun delete(id:Long)

    @Query("select * from test_data order by id desc")
    fun allContacts():LiveData<List<TestData>>

}
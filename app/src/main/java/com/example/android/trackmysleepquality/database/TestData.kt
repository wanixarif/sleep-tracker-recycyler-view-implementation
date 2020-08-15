package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "test_data")
data class TestData(

        @PrimaryKey(autoGenerate = true)
        var id:Long=0L,

        @ColumnInfo(name = "name")
        var name:String="",

        @ColumnInfo(name = "num")
        var num:Long=0L

)
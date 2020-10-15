package com.knesarcreation.attendanceapp.database

import android.content.Context
import androidx.room.Room
import kotlin.jvm.internal.Intrinsics

class DatabaseInstance {
    fun newInstance(context: Context?): Database {
        val database: Database? = null
        return Room.databaseBuilder(context!!, Database::class.java, "attendanceDatabase")
            .allowMainThreadQueries().build()
    }
}
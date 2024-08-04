package com.alexandermaynard_comp304lab3_ex1.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.patient.PatientDao
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import com.alexandermaynard_comp304lab3_ex1.Database.test.TestDao

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//database for the application. uses an array of Entities Nurse, Test and Patient to build the application
@Database(entities = arrayOf(Nurse::class, Patient::class, Test::class), version = 1, exportSchema = false)
abstract class NurseAppDatabase : RoomDatabase() {

    //abstract nurseDao reference to be used by the view models
    abstract fun nurseDao(): NurseDao
    //abstract patientDao reference view models
    abstract fun patientDao(): PatientDao
    //abstract testDao reference view models
    abstract fun testDao(): TestDao


    //make sure that there is only one instance of the database, and to make sure to build the database if there is no instance
    companion object {
        @Volatile
        private var INSTANCE: NurseAppDatabase? = null

        fun getDatabase(context: Context): NurseAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NurseAppDatabase::class.java,
                    "nurse_app_database")
                    .addMigrations()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
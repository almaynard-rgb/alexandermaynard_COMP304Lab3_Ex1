package com.alexandermaynard_comp304lab3_ex1.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao
import com.alexandermaynard_comp304lab3_ex1.Database.patient.PatientDao
import com.alexandermaynard_comp304lab3_ex1.Database.test.TestDao
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test

/*NOTE THE CONTENTS OF THIS FILE ARE A MODIFIED VERSION FROM THE BusSchedule project from the
Centennial College COMP304 section 401 class examples as well as the Android Studio Website.*/

/*TODO- Make proper attribution for this modified file (temporary one is there for now) as well as make sure that we can still use this format*/

@Database(entities = arrayOf(Nurse::class, Patient::class, Test::class), version = 1)
abstract class NurseAppDatabase : RoomDatabase() {
    abstract fun nurseDao(): NurseDao
    abstract fun patientDao(): PatientDao
    abstract fun testDao(): TestDao

    companion object {
        @Volatile
        private var INSTANCE: NurseAppDatabase? = null

        fun getDatabase(context: Context): NurseAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NurseAppDatabase::class.java,
                    "nurse_app_database")
                    .createFromAsset("database/nursing_app.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
package com.alexandermaynard_comp304lab3_ex1

import android.app.Application
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase


//make sure to comment this!!!!
class NurseApplication : Application() {
    val database: NurseAppDatabase by lazy { NurseAppDatabase.getDatabase(this) }
}
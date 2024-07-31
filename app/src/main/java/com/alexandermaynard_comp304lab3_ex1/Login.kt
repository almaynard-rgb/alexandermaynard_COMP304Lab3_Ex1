package com.alexandermaynard_comp304lab3_ex1
import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.NurseViewModel
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.NurseViewModelFactory

//private val viewModel: NurseViewModel = NurseViewModelFactory().database.nurseDao()


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Room.databaseBuilder(applicationContext, NurseAppDatabase::class.java, "Db").allowMainThreadQueries().build()

        //val nurse = Nurse(12345, "Alex", "Maynard", "Cardio", "hello123")
        //db.nurseDao().insertNurse(nurse)
        db.nurseDao().getNurseInfo(12345, "hello123")
    }
}
package com.alexandermaynard_comp304lab3_ex1
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse.NurseViewModel
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    //shared preferences for the nurseId
    lateinit var loggedInNurseIdSharedPref: SharedPreferences
    lateinit var editLoggedInNurseId: SharedPreferences.Editor

    lateinit var nurseViewModel: NurseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loggedInNurseIdSharedPref = getSharedPreferences("loggedInNurseId", Context.MODE_PRIVATE)
        editLoggedInNurseId = loggedInNurseIdSharedPref.edit() //used to edit the preferences more easily

        //val database = Room.databaseBuilder(applicationContext, NurseAppDatabase::class.java, "Db").allowMainThreadQueries().build()

        //create some nurses to start the application in case there are none
        val nurse1 = Nurse(12345, "Alex", "Maynard", "Cardio", "hello123")
        val nurse2 = Nurse(11111, "Thomas", "Maynard", "Cardio", "hello1234")

        //initialize the nurseViewModel
        nurseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NurseViewModel::class.java)

        nurseViewModel.insertNurse(nurse1)
        nurseViewModel.insertNurse(nurse2)

        //assign the login and password edit text fields.
        val nurseLoginTextView = findViewById<EditText>(R.id.nurse_username_entry)
        val nursePasswordTextView = findViewById<EditText>(R.id.nurse_password_entry)

        val loginBtn = findViewById<Button>(R.id.login_btn)
        val logoutBtn = findViewById<Button>(R.id.logout_btn)

        loginBtn.setOnClickListener {
            lifecycleScope.launch() {
                val foundNurse = nurseViewModel.getNurse(12345, "hello123")
                if(nurseLoginTextView.text.toString() == foundNurse.nurseId.toString() && nursePasswordTextView.text.toString() == foundNurse.password.toString()) {
                    editLoggedInNurseId.putString("loggedInNurseId", foundNurse.nurseId.toString()).commit()
                    Toast.makeText(applicationContext, "Login success!", Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                }
                else {
                    Toast.makeText(applicationContext, "Username or password is incorrect!", Toast.LENGTH_LONG).show()
                }
            }
        }

        logoutBtn.setOnClickListener {
            editLoggedInNurseId.putString("loggedInNurseId", "loggedOut").commit()
            Toast.makeText(applicationContext, "Logged out!", Toast.LENGTH_LONG).show()
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }
}
package com.alexandermaynard_comp304lab3_ex1
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse.NurseViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

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

        //initialize the nurseViewModel
        nurseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NurseViewModel::class.java)

        //assign the login and password edit text fields.
        val nurseLoginTextView = findViewById<EditText>(R.id.nurse_username_entry).text
        val nursePasswordTextView = findViewById<EditText>(R.id.nurse_password_entry).text

        val loginBtn = findViewById<Button>(R.id.login_btn)
        val logoutBtn = findViewById<Button>(R.id.logout_btn)

        loginBtn.setOnClickListener {

            if(nurseLoginTextView.isBlank() || nursePasswordTextView.isBlank()) {
                Toast.makeText(applicationContext, "One or more fields are blank", Toast.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    try {
                        editLoggedInNurseId.putString("loggedInNurseId", nurseViewModel.getNurse(nurseLoginTextView.toString().toInt(), nursePasswordTextView.toString())?.nurseId.toString()).commit()
                        Toast.makeText(applicationContext, "Login success!", Toast.LENGTH_LONG).show()
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                    } catch (e: Exception) {
                        Log.e("Login Exception", "Please check your password or username and make sure they are correct")
                        Toast.makeText(applicationContext, "Login failed, check your credentials", Toast.LENGTH_LONG).show()
                    }

                    //if(nurseViewModel.getNurse(nurseLoginTextView.toString().toInt(), nursePasswordTextView.toString()).toString()
                            //.isNotBlank()) {
                        //editLoggedInNurseId.putString("loggedInNurseId", nurseViewModel.getNurse(nurseLoginTextView.toString().toInt(), nursePasswordTextView.toString()).nurseId.toString()).commit()
                        //Toast.makeText(applicationContext, "Login success!", Toast.LENGTH_LONG).show()
                        //val i = Intent(applicationContext, MainActivity::class.java)
                        //startActivity(i)
                    //}
                    //else {
                        //Toast.makeText(applicationContext, "Username or password is incorrect!", Toast.LENGTH_LONG).show()
                    //}
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
package com.alexandermaynard_comp304lab3_ex1
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse.NurseViewModel
import kotlinx.coroutines.launch

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

class LoginActivity : AppCompatActivity() {

    //shared preferences for the nurseId
    private lateinit var loggedInNurseIdSharedPref: SharedPreferences
    private lateinit var editLoggedInNurseId: SharedPreferences.Editor

    //reference to the nurseViewModel
    private lateinit var nurseViewModel: NurseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //initialize the shared prefs
        loggedInNurseIdSharedPref = getSharedPreferences("loggedInNurseId", Context.MODE_PRIVATE)
        editLoggedInNurseId = loggedInNurseIdSharedPref.edit() //used to edit the preferences more easily

        //initialize the nurseViewModel
        nurseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NurseViewModel::class.java]

        //reference the login and password edit text fields.
        val nurseLoginTextView = findViewById<EditText>(R.id.nurse_username_entry).text
        val nursePasswordTextView = findViewById<EditText>(R.id.nurse_password_entry).text

        //reference the login and password buttons.
        val loginBtn = findViewById<ImageButton>(R.id.login_btn)
        val logoutBtn = findViewById<Button>(R.id.logout_btn)


        //login onclick listener to login
        loginBtn.setOnClickListener {
            //check if fields are blank
            if(nurseLoginTextView.isBlank() || nursePasswordTextView.isBlank()) {
                //send proper toast
                Toast.makeText(applicationContext, "One or more fields are blank", Toast.LENGTH_LONG).show()
            } else {
                //otherwise launch a coroutine to try login
                lifecycleScope.launch {
                    //check if the login credentials do not match
                    if(nurseViewModel.getNurse(nurseLoginTextView.toString().toInt(), nursePasswordTextView.toString()) == null) {
                        //if they don't...
                        //toast to let the user know to try again
                        Toast.makeText(applicationContext, "Login failed, check your credentials", Toast.LENGTH_LONG).show()
                    }
                    //else they are correct so...
                    else {
                        //...get the nurse id and apply it the editLoggedInNurseId shared pref
                        editLoggedInNurseId.putString("loggedInNurseId", nurseViewModel.getNurse(nurseLoginTextView.toString().toInt(), nursePasswordTextView.toString())?.nurseId.toString()).commit()
                        //toast to alert for proper login
                        Toast.makeText(applicationContext, "Login success!", Toast.LENGTH_LONG).show()
                        //intent to go back to the Main Activity
                        val i = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                    }
                }
            }
        }

        //logout onclick listener to logout
        logoutBtn.setOnClickListener {
            //set the editLoggedInNurseId to "loggedOut"
            editLoggedInNurseId.putString("loggedInNurseId", "loggedOut").commit()
            //let the user know they logged out
            Toast.makeText(applicationContext, "Logged out!", Toast.LENGTH_LONG).show()
            //intent to go back to the Main Activity
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }


    //inflate the options menu for going back the main page
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.back_to_main_menu, menu)
        return true
    }

    //provide options for when a options menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return backMenuSelected(item)
    }

    //functionality for when the options menu item is selected
    private fun backMenuSelected(item: MenuItem): Boolean {
        //check the item id
        when (item.itemId) {
            //when main option is pressed
            R.id.main_page_option -> {
                //go to the Main Screen
                val nextScreenIntent = Intent(this, MainActivity::class.java)
                startActivity(nextScreenIntent)
                return true
            }
        }
        return false
    }
}
package com.alexandermaynard_comp304lab3_ex1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse.NurseViewModel
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.patient.PatientViewModel
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.test.TestViewModel


class MainActivity : AppCompatActivity() {

    //viewmodels to access room database and initially create values.
    lateinit var patientViewModel: PatientViewModel
    lateinit var nurseViewModel: NurseViewModel
    lateinit var testViewModel: TestViewModel

    //shared preferences for the nurseId
    lateinit var loggedInNurseIdSharedPref: SharedPreferences
    lateinit var editLoggedInNurseId: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //get access to the sharedPrefs
        loggedInNurseIdSharedPref = getSharedPreferences("loggedInNurseId", Context.MODE_PRIVATE)
        editLoggedInNurseId = loggedInNurseIdSharedPref.edit() //used to edit the preferences more easily

        //initialize the patientViewModel
        patientViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PatientViewModel::class.java)

        //initialize the nurseViewModel
        nurseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NurseViewModel::class.java)

        //initialize the testViewModel
        testViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TestViewModel::class.java)

        val loginBtn = findViewById<Button>(R.id.to_login_page_btn)
        loginBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()

        if (loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut") != ""
            && loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut") != "loggedOut"
        ) {
            Toast.makeText(this, "Still logged in", Toast.LENGTH_LONG).show()
        }

        //insert initial values for the database for testing purposes
        patientViewModel.insertPatient(
            Patient(22222,"Toby","Maguire", Departments.NEURO.departments.uppercase(),12345, Rooms.ROOM1.roomNumber))
        patientViewModel.insertPatient(
            Patient(33333,"Tobias","Brent", Departments.CARDIO.departments.uppercase(),11111, Rooms.ROOM1.roomNumber))
        //new nurses inserted
        nurseViewModel.insertNurse(
            Nurse(12345, "Alex", "Maynard", Departments.NEURO.departments.uppercase(), "hello123"))
        nurseViewModel.insertNurse(
            Nurse(11111, "Thomas", "Maynard", Departments.CARDIO.departments.uppercase(), "hello1234"))
        testViewModel.insertTest(
                Test(12121, 22222, 12345, 120.80,false, 38.0, "A+", 120.0)
        )
        testViewModel.insertTest(
            Test(12122, 33333, 11111, 120.70,true, 39.4, "O+", 80.0)
        )
        testViewModel.insertTest(
            Test(12123, 22222, 12345, 110.80,false, 38.4, "A+", 100.0)
        )
        testViewModel.insertTest(
            Test(12124, 33333, 11111, 120.90,true, 37.4, "O+", 80.0)
        )
    }


    //inflate the options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    //provide options for when a options menu item is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return menuItemSelected(item)
    }

    //method that provides functionality for all the options items when clicked
    private fun menuItemSelected(item: MenuItem): Boolean {
        var loggedIn = false
        if(loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut") == ""
            || loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut") == "loggedOut") {
            loggedIn = false
        }
        else {
            loggedIn = true
        }
        //when user is logged in and check the item id
        when (item.itemId) {
            //when main option is pressed
            R.id.main_page_option -> {
                //go to the Main Screen
                if(loggedIn == true) {
                    val nextScreenIntent = Intent(this, MainActivity::class.java)
                    startActivity(nextScreenIntent)
                    return true
                }
                else {
                    val nextScreenIntent = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreenIntent)
                }
            }
            //when login option is pressed
            R.id.login_page_option -> {
                //go to the Login Screen
                val nextScreenIntent = Intent(this, LoginActivity::class.java)
                startActivity(nextScreenIntent)
                return true
            }
            //when patients page option is pressed
            R.id.patients_page_option -> {
                //go to the Patient Screen
                if (loggedIn == true) {
                    val nextScreenIntent = Intent(this, com.alexandermaynard_comp304lab3_ex1.PatientActivity::class.java)
                    startActivity(nextScreenIntent)
                    return true
                } else {
                    val nextScreenIntent = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreenIntent)
                }
            }
            //when tests page option is pressed
            R.id.tests_page_option -> {
                //go to the Test Screen
                if(loggedIn == true) {
                    val nextScreenIntent = Intent(this, TestActivity::class.java)
                    startActivity(nextScreenIntent)
                    return true
                }
                else {
                    val nextScreenIntent = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreenIntent)
                }
            }
            //when view tests info page option is pressed
            R.id.view_tests_info_page_option -> {
                //go to the ViewTestInfo Screen
                if(loggedIn == true) {
                    val nextScreenIntent = Intent(this, ViewTestInfoActivity::class.java)
                    startActivity(nextScreenIntent)
                    return true
                }
                else {
                    val nextScreenIntent = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreenIntent)
                }
            }

            //when update patient page option is pressed
            R.id.update_patient_page_option -> {
                //go to the Update Info Screen
                if(loggedIn == true) {
                    val nextScreenIntent = Intent(this, UpdateInfo::class.java)
                    startActivity(nextScreenIntent)
                    return true
                }
                else {
                    val nextScreenIntent = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreenIntent)
                }
            }
        }
        return false
    }
}
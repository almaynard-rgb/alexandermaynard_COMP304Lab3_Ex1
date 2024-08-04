package com.alexandermaynard_comp304lab3_ex1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.patient.PatientViewModel
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.test.TestViewModel
import com.alexandermaynard_comp304lab3_ex1.Enums.BloodTypeEnum
import kotlinx.coroutines.launch

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

class TestActivity : AppCompatActivity() {
    //viewmodel to access room database
    private lateinit var patientViewModel: PatientViewModel
    private lateinit var testViewModel: TestViewModel

    //shared preferences for the nurseId
    private lateinit var loggedInNurseIdSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get access to the sharedPrefs
        loggedInNurseIdSharedPref = getSharedPreferences("loggedInNurseId", Context.MODE_PRIVATE)

        //initialize the patientViewModel
        patientViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PatientViewModel::class.java]

        //initialize the testViewModel
        testViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[TestViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        //get reference to all fields that were entered
        val testIdEditText = findViewById<EditText>(R.id.test_id_entry).text
        val patientIdEditText = findViewById<EditText>(R.id.patient_id_entry).text
        val patientBplEditText = findViewById<EditText>(R.id.patient_bpl_entry).text
        val patientBhpEditText = findViewById<EditText>(R.id.patient_bhp_entry).text
        val patientTempEditText = findViewById<EditText>(R.id.patient_temperature_entry).text
        val patientBloodTypeEditText = findViewById<EditText>(R.id.patient_blood_type_entry).text
        val patientBloodSugarEditText = findViewById<EditText>(R.id.patient_blood_sugar_entry).text

        //reference to submit button
        val submitBtn = findViewById<Button>(R.id.enter_data_btn)

        //on click listener to submit the test to the room database
        submitBtn.setOnClickListener {
            //call coroutine to use the view models and to not block
            lifecycleScope.launch {
                //if any edit texts are left blank
                if(patientIdEditText.toString().isBlank()
                    || testIdEditText.toString().isBlank()
                    || patientBplEditText.toString().isBlank()
                    || patientBhpEditText.toString().isBlank()
                    || patientTempEditText.toString().isBlank()
                    || patientBloodTypeEditText.toString().isBlank()
                        || patientBloodSugarEditText.toString().isBlank()
                    ) {
                    //send proper toast if any fields are blank
                    Toast.makeText(applicationContext, "One or more fields were left empty", Toast.LENGTH_LONG).show()
                }
                //check if testId already exists
                else if(testViewModel.getTest(testIdEditText.toString().toInt())?.testId != null) {
                    Toast.makeText(applicationContext, "Test Id already exist", Toast.LENGTH_LONG).show()
                }
                //check if bhp is 'true' or 'false'
                else if(patientBhpEditText.toString() != "false" && patientBhpEditText.toString() != "true") {
                    Toast.makeText(applicationContext, "Entry for BHP must be 'true' or 'false'", Toast.LENGTH_LONG).show()
                }
                //check if bpl is in correct format
                else if(!patientBplEditText.toString().contains(".")) {
                    Toast.makeText(applicationContext, "Blood pressure must be written: 'top_val.bottom_val'", Toast.LENGTH_LONG).show()
                }
                //check if patientId doesn't exists
                else if(patientViewModel.getPatient(patientIdEditText.toString().toInt())?.patientId == null) {
                    Toast.makeText(applicationContext, "Patient Id does not exist", Toast.LENGTH_LONG).show()
                }
                else if(!bloodTypeChecks(patientBloodTypeEditText.toString())) {
                    Toast.makeText(applicationContext, "Blood type is incorrect or in improper format (ex. 'A+')", Toast.LENGTH_LONG).show()
                }
                //check if the room exits
                else if(!temperatureCheck(patientTempEditText.toString().toDouble())) {
                    Toast.makeText(applicationContext, "Not proper format OR Temperature cannot exceed what is possible (35-47 Celsius)", Toast.LENGTH_LONG).show()
                } else {
                    //otherwise submit to the test object to the table in the database
                    testViewModel.insertTest(
                        Test(
                            testIdEditText.toString().toInt(),
                            patientIdEditText.toString().toInt(),
                            //use shared prefs here for the logged in nurse
                            loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut").toString().toInt(),
                            patientBplEditText.toString().toDouble(),
                            patientBhpEditText.toString().toBoolean(),
                            patientTempEditText.toString().toDouble(),
                            patientBloodTypeEditText.toString(),
                            patientBloodSugarEditText.toString().toDouble()
                        )
                    )
                    //let the user know that the test was submitted correctly
                    Toast.makeText(applicationContext, "New Test data created",Toast.LENGTH_LONG).show()
                    //clear all edit texts
                    patientIdEditText.clear()
                    testIdEditText.clear()
                    patientBplEditText.clear()
                    patientBhpEditText.clear()
                    patientTempEditText.clear()
                    patientBloodTypeEditText.clear()
                    patientBloodSugarEditText.clear()
                }
            }
        }
    }

    //check to make sure the blood type is validated (blood types that exist
    private fun bloodTypeChecks(bloodTypeToCheck: String): Boolean {
        val bloodTypes = BloodTypeEnum.values()

        return (bloodTypes.any { it.bloodType == bloodTypeToCheck })
    }

    //check to make sure the temperature is validated (temperature that is possible)
    private fun temperatureCheck(tempToCheck: Double): Boolean {
        return tempToCheck in 35.0..47.0
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
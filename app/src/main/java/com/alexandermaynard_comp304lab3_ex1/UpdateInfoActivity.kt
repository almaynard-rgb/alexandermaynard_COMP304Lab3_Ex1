package com.alexandermaynard_comp304lab3_ex1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse.NurseViewModel
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.patient.PatientViewModel
import kotlinx.coroutines.launch

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//viewmodels to access room database
lateinit var patientViewModel: PatientViewModel
lateinit var nurseViewModel: NurseViewModel

class UpdateInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //initialize the patientViewModel
        patientViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PatientViewModel::class.java]

        //initialize the nurseViewModel
        nurseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NurseViewModel::class.java]


        //initialize the search bar to later return a patient
        val searchEditText = findViewById<EditText>(R.id.patient_id_search)

        //find the search result text view to display the found patient
        val searchResultText = findViewById<TextView>(R.id.patient_result)
        searchResultText.text = ""

        //initialize the search button to later search for a patient
        val searchBtn = findViewById<Button>(R.id.search_patient_btn)

        //click listener for the searchBtn
        searchBtn.setOnClickListener {
            lifecycleScope.launch {
                //if successful
                if (patientViewModel.getPatient(searchEditText.text.toString().toInt()) != null) {
                    val retrievedPatientFromRoom = patientViewModel.getPatient(searchEditText.text.toString().toInt())
                    if(searchEditText.text.toString().toInt() == retrievedPatientFromRoom?.patientId) {
                        searchResultText.text = retrievedPatientFromRoom.toString()
                    }
                } else {
                    //tell user the proper no patient exists message
                    Toast.makeText(applicationContext, "Patient does not exist in registry", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //get reference to all fields that were entered
        val patientIdEditText = findViewById<EditText>(R.id.patient_id_entry).text
        val patientDepartmentEditText = findViewById<EditText>(R.id.patient_department_entry).text
        val patientNurseIdEditText  = findViewById<EditText>(R.id.patient_nurse_id).text
        val patientRoomEditText = findViewById<EditText>(R.id.patient_room_entry).text

        //reference to the submit button
        val submitBtn = findViewById<Button>(R.id.submit_btn)
        submitBtn.setOnClickListener {
            //call coroutine to use the view models and to not block
            lifecycleScope.launch {
                //if any edit texts are left blank
                if(patientIdEditText.toString().isBlank()
                    || patientNurseIdEditText.toString().isBlank()
                    || patientDepartmentEditText.toString().isBlank()
                    || patientRoomEditText.toString().isBlank()) {
                    //send proper toast if any fields are blank
                    Toast.makeText(applicationContext, "One or more fields were left empty", Toast.LENGTH_LONG).show()
                }
                //check if patientId doesn't already exist
                else if(patientViewModel.getPatient(patientIdEditText.toString().toInt())?.patientId == null) {
                    Toast.makeText(applicationContext, "Patient Id does not exist and cannot change", Toast.LENGTH_LONG).show()
                }
                //check if nurse exists
                else if(nurseViewModel.nurseIdCheck(patientNurseIdEditText.toString().toInt())?.nurseId == null) {
                    Toast.makeText(applicationContext, "Nurse Id does not exist", Toast.LENGTH_LONG).show()
                }
                //check to make sure the department exists
                else if(!departmentChecks(patientDepartmentEditText.toString())) {
                    Toast.makeText(applicationContext, "Departments must be 'Cardio', 'Neuro' or 'Physio'! ", Toast.LENGTH_LONG).show()
                }
                //check if the room exists
                else if(!roomChecks(patientRoomEditText.toString())) {
                    Toast.makeText(applicationContext, "There are only 20 rooms!", Toast.LENGTH_LONG).show()
                } else {
                    //otherwise submit to the nurse object to the table in the database
                    patientViewModel.updatePatient(
                        Patient(
                            patientIdEditText.toString().toInt(),
                            patientViewModel.getPatient(patientIdEditText.toString().toInt())!!.firstname,
                            patientViewModel.getPatient(patientIdEditText.toString().toInt())!!.lastname,
                            patientDepartmentEditText.toString().uppercase(),
                            patientNurseIdEditText.toString().toInt(),
                            patientRoomEditText.toString().toInt()
                        )
                    )
                    //let the user know that the patient update was submitted correctly
                    Toast.makeText(
                        applicationContext,
                        "Patient data updated",
                        Toast.LENGTH_LONG
                    ).show()
                    //clear all edit texts
                    patientIdEditText.clear()
                    patientNurseIdEditText.clear()
                    patientDepartmentEditText.clear()
                    patientRoomEditText.clear()
                }
            }
        }
    }

    //check if department exists
    private fun departmentChecks(departmentToCheck: String): Boolean {
        val departments = Departments.entries.map { it.name.uppercase() }

        return departments.contains(departmentToCheck.uppercase())
    }

    //check if room exists
    private fun roomChecks(roomsToCheck: String): Boolean {
        val rooms = Rooms.entries.map { it.name }

        return rooms.contains("ROOM${roomsToCheck}")
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
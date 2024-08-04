package com.alexandermaynard_comp304lab3_ex1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.patient.PatientViewModel
import kotlinx.coroutines.launch

class PatientActivity : AppCompatActivity() {
    //viewmodel to access room database
    lateinit var patientViewModel: PatientViewModel

    //shared preferences for the nurseId
    lateinit var loggedInNurseIdSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get access to the sharedPrefs
        loggedInNurseIdSharedPref = getSharedPreferences("loggedInNurseId", Context.MODE_PRIVATE)

        //initialize the patientViewModel
        patientViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            PatientViewModel::class.java)

        val searchEditText = findViewById<EditText>(R.id.patient_id_search)

        val searchResultText = findViewById<TextView>(R.id.patient_result)
        searchResultText.text = ""

        val searchBtn = findViewById<Button>(R.id.search_patient_btn)

        searchBtn.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val retrievedPatientFromRoom = patientViewModel.getPatient(searchEditText.text.toString().toInt())
                    if(searchEditText.text.toString().toInt() == retrievedPatientFromRoom?.patientId) {
                        searchResultText.text = retrievedPatientFromRoom.toString()
                    }
                } catch (e: Exception) {
                    Log.e("Room Exception", e.toString())
                    Toast.makeText(applicationContext, "Patient does not exist in registry", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //get reference to all fields that were entered
        val patientIdEditText = findViewById<EditText>(R.id.patient_id_entry).text
        val patientFirstnameEditText = findViewById<EditText>(R.id.patient_firstname_entry).text
        val patientLastnameEditText = findViewById<EditText>(R.id.patient_lastname_entry).text
        val patientDepartmentEditText = findViewById<EditText>(R.id.patient_department_entry).text
        val patientRoomEditText = findViewById<EditText>(R.id.patient_room_entry).text

        val submitBtn = findViewById<Button>(R.id.submit_btn)
        submitBtn.setOnClickListener {
            lifecycleScope.launch {
                //if any edit texts are left blank
                if(patientIdEditText.toString().isBlank()
                    || patientFirstnameEditText.toString().isBlank()
                    || patientLastnameEditText.toString().isBlank()
                    || patientDepartmentEditText.toString().isBlank()
                    || patientRoomEditText.toString().isBlank()) {
                    Toast.makeText(applicationContext, "One or more fields were left empty", Toast.LENGTH_LONG).show()
                }
                //check if patientId already exists
                else if(patientViewModel.getPatient(patientIdEditText.toString().toInt())?.patientId != null) {
                    Toast.makeText(applicationContext, "Patient Id already exist", Toast.LENGTH_LONG).show()
                }
                else if(!departmentChecks(patientDepartmentEditText.toString())) {
                    Toast.makeText(applicationContext, "Departments must be 'Cardio', 'Neuro' or 'Physio'! ", Toast.LENGTH_LONG).show()
                }
                //room check
                else if(!roomChecks(patientRoomEditText.toString())) {
                    Toast.makeText(applicationContext, "There are only 20 rooms!", Toast.LENGTH_LONG).show()
                } else {
                    patientViewModel.insertPatient(
                        Patient(
                            patientIdEditText.toString().toInt(),
                            patientFirstnameEditText.toString(),
                            patientLastnameEditText.toString(),
                            patientDepartmentEditText.toString().uppercase(),
                            loggedInNurseIdSharedPref.getString("loggedInNurseId", "loggedOut")!!.toInt(),
                            patientRoomEditText.toString().toInt()
                        )
                    )
                    Toast.makeText(
                        applicationContext,
                        "New patient data created",
                        Toast.LENGTH_LONG
                    ).show()
                    patientIdEditText.clear()
                    patientFirstnameEditText.clear()
                    patientLastnameEditText.clear()
                    patientDepartmentEditText.clear()
                    patientRoomEditText.clear()
                }
            }
        }
    }

    fun departmentChecks(departmentToCheck: String): Boolean {
        val departments = Departments.entries.map() { it.name.uppercase() }

        return if (departments.contains(departmentToCheck.uppercase())) {
            true
        } else {
            false
        }
    }
    fun roomChecks(roomsToCheck: String): Boolean {
        val rooms = Rooms.entries.map() { it.name }

        return if (rooms.contains("ROOM${roomsToCheck}")) {
            true
        } else {
            false
        }
    }
}
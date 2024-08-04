package com.alexandermaynard_comp304lab3_ex1

import android.annotation.SuppressLint
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
import com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.test.TestViewModel
import kotlinx.coroutines.launch

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

class ViewTestInfoActivity : AppCompatActivity() {

    //viewmodel to access room database
    lateinit var testViewModel: TestViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_test_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //initialize the testViewModel
        testViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TestViewModel::class.java)

        //get reference to the patientId in the search bar
        val patientIdEditText = findViewById<EditText>(R.id.patient_id_search_entry).text

        //get a reference to the scrollview
        val testsResultsTextView = findViewById<TextView>(R.id.tests_results_textview)

        //get reference to the search patient test btn
        val searchPatientTestBtn = findViewById<Button>(R.id.search_patient_test_btn)

        //onclick listener to get all test info related to the patientId that was passed
        searchPatientTestBtn.setOnClickListener {
            //reset the string for the results
            testsResultsTextView.text = ""

            //launch coroutine to use the test view model
            lifecycleScope.launch {
                //check if the there are any tests first
                if(!testViewModel.getAllTests(patientIdEditText.toString().toInt()).isNullOrEmpty()) {
                    //if so map the results to a listOfTests
                    val listOfTests = testViewModel.getAllTests(patientIdEditText.toString().toInt())?.map { it }
                    //another null check to make sure values are being passed
                    if (listOfTests != null) {
                        //for loop to concatenate all values to the text view in the scroll view area
                        for (tests in listOfTests.listIterator()) {
                            testsResultsTextView.text = "${testsResultsTextView.text}\n ${tests}"
                        }
                    }
                    //if there are no tests associated with the patient id passed
                } else {
                    //toast to let the user know that the test Id entered does not exist
                    Toast.makeText(applicationContext, "The Test Id you entered does not exist", Toast.LENGTH_LONG).show()
                }
            }
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
    fun backMenuSelected(item: MenuItem): Boolean {
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
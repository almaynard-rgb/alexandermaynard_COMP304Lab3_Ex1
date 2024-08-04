package com.alexandermaynard_comp304lab3_ex1

import android.annotation.SuppressLint
import android.os.Bundle
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
        searchPatientTestBtn.setOnClickListener {
            testsResultsTextView.text = ""
            lifecycleScope.launch {

                if(!testViewModel.getAllTests(patientIdEditText.toString().toInt()).isNullOrEmpty()) {
                    val listOfTests = testViewModel.getAllTests(patientIdEditText.toString().toInt())?.map { it }

                    if (listOfTests != null) {
                        for (tests in listOfTests.listIterator()) {
                            testsResultsTextView.text = "${testsResultsTextView.text}\n ${tests}"
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "The Test Id you entered does not exist", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
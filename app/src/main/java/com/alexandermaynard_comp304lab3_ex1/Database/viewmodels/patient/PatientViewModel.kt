package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.patient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.patient.PatientRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//Patient will interact with the Views and Patient Repository to keep necessary information
class PatientViewModel(application: Application): AndroidViewModel(application) {
    //instance of the repository
    val patientRepo: PatientRepository

    //initialize variables
    init {
        //initialize the dao by using the instance of the NurseAppDatabase.getDatabase().patientDao
        val patientDao = NurseAppDatabase.getDatabase(application).patientDao()
        //assign the repository
        patientRepo = PatientRepository(patientDao)
    }

    //get a patient from the repository
    suspend fun getPatient(patientId: Int): Patient? {
        val deferredPatient: Deferred<Patient?> = viewModelScope.async {
            patientRepo.getPatient(patientId)
        }
        return deferredPatient.await()
    }

    //insert a patient from the repository
    fun insertPatient(patient: Patient) = viewModelScope.launch(Dispatchers.IO) {
        patientRepo.insertPatient(patient)
    }

    //update a patient from the repository
    fun updatePatient(patient: Patient) = viewModelScope.launch {
        patientRepo.updatePatient(patient)
    }
}
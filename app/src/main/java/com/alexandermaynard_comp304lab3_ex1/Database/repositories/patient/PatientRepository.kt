package com.alexandermaynard_comp304lab3_ex1.Database.repositories.patient

import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.patient.PatientDao

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//PatientRepository for the viewmodel to interact with the PatientDao
class PatientRepository(private val patientDao: PatientDao) {

    //get a single Patient object by passing a patientId
    suspend fun getPatient(patientId: Int): Patient? {
        return patientDao.getPatientInfo(patientId)
    }

    //insert a new Patient object by passing a new Patient object
    suspend fun insertPatient(patient: Patient) {
        patientDao.insertPatientInfo(patient)
    }

    //update a Patient object(by passing a new Patient object to update the previous one)
    suspend fun updatePatient(patient: Patient) {
        patientDao.updatePatientInfo(patient)
    }
}
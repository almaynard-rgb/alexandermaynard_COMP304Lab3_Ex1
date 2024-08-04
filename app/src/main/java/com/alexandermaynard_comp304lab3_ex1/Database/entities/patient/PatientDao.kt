package com.alexandermaynard_comp304lab3_ex1.Database.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//PatientDao to interact with Patient table in the room database
@Dao
interface PatientDao {

    //select all patient items where the patientId matches the one provided
    @Query("SELECT * FROM patient WHERE patient_id = :patientId")
    suspend fun getPatientInfo(patientId: Int): Patient?

    //insert a patient object that is provided to the Patient table
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignore if there is conflict
    suspend fun insertPatientInfo(patient: Patient)

    //update a patients information by passing a new patient object
    @Update(onConflict = OnConflictStrategy.REPLACE) //replace if there is conflict
    suspend fun updatePatientInfo(patient: Patient)
}
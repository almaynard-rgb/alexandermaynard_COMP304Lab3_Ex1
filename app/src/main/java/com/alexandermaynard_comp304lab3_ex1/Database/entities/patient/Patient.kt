package com.alexandermaynard_comp304lab3_ex1.Database.patient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//Patient Entity data class for use by room database
@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @ColumnInfo(name = "room") val room: Int
) {
    //override of the toString method to custom display the patient information
    override fun toString(): String {
        return "Name: $firstname $lastname Nurse: $nurseId\nDepartment: $department  Room: $room"
    }
}
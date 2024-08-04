package com.alexandermaynard_comp304lab3_ex1.Database.nurse

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

//Nurse Entity data class for use by room database
@Entity
data class Nurse(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "password") val password: String
)
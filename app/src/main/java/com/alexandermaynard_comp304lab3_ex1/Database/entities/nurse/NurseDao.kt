package com.alexandermaynard_comp304lab3_ex1.Database.nurse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//NurseDao to interact with Patient table in the room database
@Dao
interface NurseDao {
    //to retrieve the nurse info if an id and password exists and the password is correct
    @Query("SELECT * FROM nurse WHERE nurse_id = :nurseId AND password = :password")
    suspend fun getNurseInfo(nurseId: Int, password: String): Nurse?

    //check if nurse is there by passing a nurseId
    @Query("SELECT * FROM nurse WHERE nurse_id = :nurseId")
    suspend fun getNurseById(nurseId: Int): Nurse?

    //insert a nurse object that is provided to the Nurse table
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignore if there is conflict
    suspend fun insertNurseInfo(nurse: Nurse)
}
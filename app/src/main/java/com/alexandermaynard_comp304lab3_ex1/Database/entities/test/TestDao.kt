package com.alexandermaynard_comp304lab3_ex1.Database.test

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

//TestDao to interact with Patient table in the room database
@Dao
interface TestDao {
    //insert a test object that is provided to the Test table
    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignore if there is conflict
    suspend fun insertTestItem(test: Test)

    //get all tests with a matching patientId to the one passed
    @Query("SELECT * FROM test WHERE patient_id = :patientId ORDER BY test_id")
    suspend fun getAllTestInfo(patientId: Int): List<Test>?

    //get a test with a matching testId to the one passed
    @Query("SELECT * FROM test WHERE test_id = :testId")
    suspend fun getTestInfo(testId: Int): Test?
}
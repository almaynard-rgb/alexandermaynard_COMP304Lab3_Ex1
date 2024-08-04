package com.alexandermaynard_comp304lab3_ex1.Database.repositories.test

import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import com.alexandermaynard_comp304lab3_ex1.Database.test.TestDao

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//TestRepository for the viewmodel to interact with the TestDao
class TestRepository(private val testDao: TestDao) {
    //insert a new test object by passing a new test object
    suspend fun insertTest(test: Test) {
        testDao.insertTestItem(test)
    }

    //get all test object by passing a testId
    suspend fun getAllTests(testId: Int): List<Test>? {
        return testDao.getAllTestInfo(testId)
    }

    //get a single test object by passing a testId
    suspend fun getTest(testId: Int): Test? {
        return testDao.getTestInfo(testId)
    }
}
package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.test.TestRepository
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
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

//TestViewModel will interact with the Views and Test Repository to keep necessary information
class TestViewModel(application: Application): AndroidViewModel(application) {
    //instance of the repository
    val testRepo: TestRepository

    //initialize variables
    init {
        //initialize the dao by using the instance of the NurseAppDatabase.getDatabase().testDao
        val testDao = NurseAppDatabase.getDatabase(application).testDao()
        //assign the repository
        testRepo = TestRepository(testDao)
    }

    //insert a test from the repository
    fun insertTest(test: Test) = viewModelScope.launch(Dispatchers.IO) {
        testRepo.insertTest(test)
    }

    //single test returned to check if tests exist from the repository
    suspend fun getTest(testId: Int): Test? {
        val deferredTest: Deferred<Test?> = viewModelScope.async {
            testRepo.getTest(testId)
        }
        return deferredTest.await()
    }

    //get all tests from the repository
    suspend fun getAllTests(testId: Int): List<Test>? {
        val deferredTests: Deferred<List<Test>?> = viewModelScope.async {
            testRepo.getAllTests(testId)
        }
        return deferredTests.await()
    }
}
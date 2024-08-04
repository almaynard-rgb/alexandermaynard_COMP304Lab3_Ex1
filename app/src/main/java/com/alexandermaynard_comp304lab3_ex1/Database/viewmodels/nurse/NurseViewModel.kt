package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.nurse.NurseRepository
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

//NurseViewModel will interact with the Views and Nurse Repository to keep necessary information
class NurseViewModel(application: Application): AndroidViewModel(application) {
    //instance of the repository
    val nurseRepo: NurseRepository

    //initialize variables
    init {
        //initialize the dao by using the instance of the NurseAppDatabase.getDatabase().nurseDao
        val nurseDao = NurseAppDatabase.getDatabase(application).nurseDao()
        //assign the repository
        nurseRepo = NurseRepository(nurseDao)
    }

    //get nurse by nurseId and password from the repository
    suspend fun getNurse(nurseId: Int, password: String): Nurse? {
        val deferredNurse: Deferred<Nurse?> = viewModelScope.async {
            nurseRepo.getNurse(nurseId, password)
        }
        return deferredNurse.await()
    }

    //get nurse (as a check) by nurseId from the repository
    suspend fun nurseIdCheck(nurseId: Int): Nurse? {
        val deferredNurse: Deferred<Nurse?> = viewModelScope.async {
            nurseRepo.getNurseId(nurseId)
        }
        return deferredNurse.await()
    }

    //insert a nurse from the repository
    fun insertNurse(nurse: Nurse) = viewModelScope.launch(Dispatchers.IO){
        nurseRepo.insertNurse(nurse)
    }
}
package com.alexandermaynard_comp304lab3_ex1.Database.repositories.nurse

import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao

/*
* Student ID: 301170707
* Student Name: Alexander Maynard
* Class: COMP304 - Section 401
* Assignment: Lab Assignment 3 - Exercise 1
* Professor: Parth Padhiyar
*/

//NurseRepository for the viewmodel to interact with the NurseDao
class NurseRepository(private val nurseDao: NurseDao) {

    //get Nurse object (used as a login) by passing a nurseId and password
    suspend fun getNurse(nurseId: Int, password: String): Nurse? {
        return nurseDao.getNurseInfo(nurseId, password)
    }

    /*get a Nurse by nurseId (used to validate if a
    nurse is valid without needing the password aka
    when a nurse is already logged in) by
    passing a nurseId*/
    suspend fun getNurseId(nurseId: Int): Nurse? {
        return nurseDao.getNurseById(nurseId)
    }

    //insert a Nurse object by passing a new Nurse object
    suspend fun insertNurse(nurse: Nurse) {
        nurseDao.insertNurseInfo(nurse)
    }
}
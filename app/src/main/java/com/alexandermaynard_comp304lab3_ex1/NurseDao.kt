package com.alexandermaynard_comp304lab3_ex1

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NurseDao {

    //to retrieve the nurse info if an id and password exists and the password is correct
    @Query("SELECT * FROM nurse WHERE nurse_id AND password = :nurseId AND :password")
    fun getNurseInfo(nurseId: Int, password: String): Flow<List<Nurse>>
}
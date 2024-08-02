package com.alexandermaynard_comp304lab3_ex1.Database.nurse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NurseDao {

    //to retrieve the nurse info if an id and password exists and the password is correct
    @Query("SELECT * FROM nurse WHERE nurse_id = :nurseId AND password = :password")
    suspend fun getNurseInfo(nurseId: Int, password: String): Nurse

    //insert for testing purposes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNurseInfo(nurse: Nurse)
}
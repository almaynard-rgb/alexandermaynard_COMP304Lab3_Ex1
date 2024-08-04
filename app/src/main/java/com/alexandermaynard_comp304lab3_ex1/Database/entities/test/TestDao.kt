package com.alexandermaynard_comp304lab3_ex1.Database.test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TestDao {
    //insert a test
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTestItem(test: Test)

    //get all tests
    @Query("SELECT * FROM test WHERE patient_id = :patientId ORDER BY test_id")
    suspend fun getAllTestInfo(patientId: Int): List<Test>?

    @Query("SELECT * FROM test WHERE test_id = :testId")
    suspend fun getTestInfo(testId: Int): Test?
}
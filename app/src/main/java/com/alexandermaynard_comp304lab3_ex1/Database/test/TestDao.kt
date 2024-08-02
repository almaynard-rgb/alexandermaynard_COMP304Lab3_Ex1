package com.alexandermaynard_comp304lab3_ex1.Database.test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    //TODO - Change function to un insertTestForPatient(testId: Int, patientId: Int, nurseId: Int, bPL: Double, bHP: Boolean, temperature: Temperature, bloodType: String, bloodSugarLevel: Double) : Flow<Test>
    /*TO-DO: ADD a check to see if the patient exists in patient*/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTestForPatient(test: Test)


    @Query("SELECT * FROM test WHERE patient_id = :patientId ORDER BY test_id")
    fun getTestForPatient(patientId: Int): Flow<List<Test>>
}
package com.alexandermaynard_comp304lab3_ex1

import android.health.connect.datatypes.units.Temperature
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    /*TO-DO: ADD a check to see if the patient exists in patient*/
    @Query("INSERT INTO test VALUES (:testId, :patientId, :nurseId, :bPL, :bHP, :temperature, :bloodType, :bloodSugarLevel)")
    fun insertTestForPatient(testId: Int, patientId: Int, nurseId: Int, bPL: Double, bHP: Boolean, temperature: Temperature, bloodType: String, bloodSugarLevel: Double) : Flow<Test>

    @Query("SELECT * FROM test WHERE patient_id = :patientId ORDER BY test_id")
    fun getTestForPatient(patientId: Int): Flow<List<Test>>
}
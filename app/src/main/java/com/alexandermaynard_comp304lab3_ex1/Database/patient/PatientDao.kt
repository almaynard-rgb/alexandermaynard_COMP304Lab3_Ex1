package com.alexandermaynard_comp304lab3_ex1.Database.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient WHERE patient_id = :patientId")
    fun getPatientInfo(patientId: Int): Flow<Patient>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPatientInfo(patient: Patient)

    @Update
    fun updatePatientInfo(patient: Patient)
}
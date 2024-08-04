package com.alexandermaynard_comp304lab3_ex1.Database.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PatientDao {

    @Query("SELECT * FROM patient WHERE patient_id = :patientId")
    suspend fun getPatientInfo(patientId: Int): Patient?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPatientInfo(patient: Patient)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updatePatientInfo(patient: Patient)
}
package com.alexandermaynard_comp304lab3_ex1.Database.repositories.patient

import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.patient.PatientDao

class PatientRepository(private val patientDao: PatientDao) {

    //get patient
    suspend fun getPatient(patientId: Int): Patient? {
        return patientDao.getPatientInfo(patientId)
    }

    //insert patient
    suspend fun insertPatient(patient: Patient) {
        patientDao.insertPatientInfo(patient)
    }

    suspend fun updatePatient(patient: Patient) {
        patientDao.updatePatientInfo(patient)
    }
}
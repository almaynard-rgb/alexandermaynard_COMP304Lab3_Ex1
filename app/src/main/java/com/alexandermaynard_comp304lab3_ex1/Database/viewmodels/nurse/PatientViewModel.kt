package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.patient.Patient
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.patient.PatientRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PatientViewModel(application: Application): AndroidViewModel(application) {
    val patientRepo: PatientRepository

    init {
        val patientDao = NurseAppDatabase.getDatabase(application).patientDao()
        patientRepo = PatientRepository(patientDao)
    }

    suspend fun getPatient(patientId: Int): Patient? {
        val deferredNurse: Deferred<Patient?> = viewModelScope.async {
            patientRepo.getPatient(patientId)
        }
        return deferredNurse.await()
    }

    fun insertPatient(patient: Patient) = viewModelScope.launch(Dispatchers.IO) {
        patientRepo.insertPatient(patient)
    }

    fun updatePatient(patient: Patient) = viewModelScope.launch {
        patientRepo.updatePatient(patient)
    }
}
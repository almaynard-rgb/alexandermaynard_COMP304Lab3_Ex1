package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.nurse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.nurse.NurseRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class NurseViewModel(application: Application): AndroidViewModel(application) {
    val nurseRepo: NurseRepository

    init {
        val nurseDao = NurseAppDatabase.getDatabase(application).nurseDao()
        nurseRepo = NurseRepository(nurseDao)
    }

    suspend fun getNurse(nurseId: Int, password: String): Nurse {
        val deferredNurse: Deferred<Nurse> = viewModelScope.async {
            nurseRepo.getNurse(nurseId, password)
        }
        return deferredNurse.await()
    }

    fun insertNurse(nurse: Nurse) = viewModelScope.launch(Dispatchers.IO){
        nurseRepo.insertNurse(nurse)
    }
}
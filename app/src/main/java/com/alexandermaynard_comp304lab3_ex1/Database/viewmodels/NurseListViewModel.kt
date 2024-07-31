package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao
import kotlinx.coroutines.flow.Flow

class NurseViewModel(private val nurseDao: NurseDao): ViewModel() {
    fun getNurse(username: Int, password: String): Flow<List<Nurse>> = nurseDao.getNurseInfo(username, password)
}

class NurseViewModelFactory(
    private val nurseDao: NurseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NurseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NurseViewModel(nurseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
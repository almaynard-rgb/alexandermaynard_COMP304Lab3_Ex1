package com.alexandermaynard_comp304lab3_ex1.Database.repositories.nurse

import com.alexandermaynard_comp304lab3_ex1.Database.nurse.Nurse
import com.alexandermaynard_comp304lab3_ex1.Database.nurse.NurseDao

class NurseRepository(private val nurseDao: NurseDao) {
    //get nurse
    suspend fun getNurse(nurseId: Int, password: String): Nurse? {
        return nurseDao.getNurseInfo(nurseId, password)
    }

    suspend fun getNurseId(nurseId: Int): Nurse? {
        return nurseDao.getNurseById(nurseId)
    }

    //insert nurse
    suspend fun insertNurse(nurse: Nurse) {
        nurseDao.insertNurseInfo(nurse)
    }

    suspend fun updateNurse(nurse: Nurse) {
        nurseDao.updateNurseInfo(nurse)
    }
}
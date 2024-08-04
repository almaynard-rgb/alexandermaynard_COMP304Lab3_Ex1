package com.alexandermaynard_comp304lab3_ex1.Database.repositories.test

import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import com.alexandermaynard_comp304lab3_ex1.Database.test.TestDao

class TestRepository(private val testDao: TestDao) {
    suspend fun insertTest(test: Test) {
        testDao.insertTestItem(test)
    }

    suspend fun getAllTests(testId: Int): List<Test>? {
        return testDao.getAllTestInfo(testId)
    }
    suspend fun getTest(testId: Int): Test? {
        return testDao.getTestInfo(testId)
    }
}
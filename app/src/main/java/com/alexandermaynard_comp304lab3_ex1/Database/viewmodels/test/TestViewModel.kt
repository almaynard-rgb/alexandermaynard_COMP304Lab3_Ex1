package com.alexandermaynard_comp304lab3_ex1.Database.viewmodels.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexandermaynard_comp304lab3_ex1.Database.NurseAppDatabase
import com.alexandermaynard_comp304lab3_ex1.Database.repositories.test.TestRepository
import com.alexandermaynard_comp304lab3_ex1.Database.test.Test
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TestViewModel(application: Application): AndroidViewModel(application) {
    val testRepo: TestRepository

    init {
        val testDao = NurseAppDatabase.getDatabase(application).testDao()
        testRepo = TestRepository(testDao)
    }

    //insert a test
    fun insertTest(test: Test) = viewModelScope.launch(Dispatchers.IO) {
        testRepo.insertTest(test)
    }

    //single test returned to check if tests exist
    suspend fun getTest(testId: Int): Test? {
        val deferredTest: Deferred<Test?> = viewModelScope.async {
            testRepo.getTest(testId)
        }
        return deferredTest.await()
    }

    suspend fun getAllTests(testId: Int): List<Test>? {
        val deferredTests: Deferred<List<Test>?> = viewModelScope.async {
            testRepo.getAllTests(testId)
        }
        return deferredTests.await()
    }
}
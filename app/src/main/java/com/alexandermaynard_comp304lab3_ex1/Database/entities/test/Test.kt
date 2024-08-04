package com.alexandermaynard_comp304lab3_ex1.Database.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Test(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "test_id") val testId: Int,
    @ColumnInfo(name = "patient_id") val patientId: Int,
    @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @ColumnInfo(name = "bpl") val bPL: Double,
    @ColumnInfo(name = "bph") val bPH: Boolean,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "blood_type") val bloodType: String,
    @ColumnInfo(name = "blood_sugar_level") val bloodSugarLevel: Double
) {
    override fun toString(): String {
        return "TestId: $testId Nurse: $nurseId\nBPL: $bPL  BPH: $bPH Temp: $temperature\n Blood Type: $bloodType Blood Sugar: $bloodSugarLevel\n\n"
    }
}
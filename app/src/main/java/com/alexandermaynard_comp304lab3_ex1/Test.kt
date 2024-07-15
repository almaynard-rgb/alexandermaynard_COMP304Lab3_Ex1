package com.alexandermaynard_comp304lab3_ex1

import android.health.connect.datatypes.units.Temperature
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


//TO-DO ADD foreign keys!!!!
@Entity
data class Test(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "test_id") val testId: Int,
    @NonNull @ColumnInfo(name = "patient_id") val patientId: Int,
    @NonNull @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @NonNull @ColumnInfo(name = "bpl") val bPL: Double,
    @NonNull @ColumnInfo(name = "bpl") val bHP: Boolean,
    @NonNull @ColumnInfo(name = "temperature") val temperature: Temperature,
    @NonNull @ColumnInfo(name = "blood_type") val bloodType: String,
    @NonNull @ColumnInfo(name = "blood_sugar_level") val bloodSugarLevel: Double
)
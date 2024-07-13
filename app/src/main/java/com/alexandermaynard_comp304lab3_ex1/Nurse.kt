package com.alexandermaynard_comp304lab3_ex1

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TO-DO ADD foreign keys!!!!
@Entity
data class Nurse(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @NonNull @ColumnInfo(name = "firstname") val firstname: String,
    @NonNull @ColumnInfo(name = "lastname") val lastname: String,
    @NonNull @ColumnInfo(name = "department") val department: String,
    @NonNull @ColumnInfo(name = "password") val password: String
)
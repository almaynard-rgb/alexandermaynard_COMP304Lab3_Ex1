package com.alexandermaynard_comp304lab3_ex1.Database.nurse

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TO-DO ADD foreign keys!!!!
@Entity
data class Nurse(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "nurse_id") val nurseId: Int,
    @ColumnInfo(name = "firstname") val firstname: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "password") val password: String
)
package au.edu.kbs.mobiledevelopment.employeeapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "employees")
@Parcelize
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val jobRole: String
): Parcelable

//Parcelization to be able to convert complex objects in simple data to pass among activities/fragments

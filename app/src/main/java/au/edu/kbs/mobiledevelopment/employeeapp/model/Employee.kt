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
    val jobRole: String,
    val imgReference: String,
    val initials: String,
    val phoneNumber: String,
    val email: String,
    val department: String,
    val hireDate: String,
    val contractType: String,
    val salary: Double,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
): Parcelable

//Parcelization to be able to convert complex objects in simple data to pass among activities/fragments

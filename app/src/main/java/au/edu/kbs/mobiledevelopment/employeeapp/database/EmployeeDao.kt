package au.edu.kbs.mobiledevelopment.employeeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee

@Dao
interface EmployeeDao {
    // it allows to create queries to access data from database such as read, create, update & delete data for employees

    // Create employee (Insert)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    // Update employee
    @Update
    suspend fun updateEmployee(employee: Employee)

    // Delete employee
    @Delete
    suspend fun deleteEmployee(employee: Employee)

    // Get All employees
    @Query("SELECT * FROM EMPLOYEES ORDER BY lastName ASC")
    fun getAllEmployees(): LiveData<List<Employee>>

    // Search employees by id, first name and last name
    @Query("SELECT * FROM EMPLOYEES WHERE id LIKE :query or firstName LIKE :query or lastName LIKE :query")
    fun searchEmployee(query: String?): LiveData<List<Employee>>

}
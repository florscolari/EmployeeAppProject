package au.edu.kbs.mobiledevelopment.employeeapp.repository

import au.edu.kbs.mobiledevelopment.employeeapp.database.EmployeeDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee


// A place where data will be contained
class EmployeeRepository(private val db: EmployeeDatabase) {
    // I'm calling the methods defined on EmployeeDao interface and I'll be using from here as functions

    // Create Employee function
    suspend fun insertEmployee(employee: Employee) = db.getEmployeeDao().insertEmployee(employee)

    // Delete Employee function
    suspend fun deleteEmployee(employee: Employee) = db.getEmployeeDao().deleteEmployee(employee)

    // Update Employee function
    suspend fun updateEmployee(employee: Employee) = db.getEmployeeDao().updateEmployee(employee)

    // Get All Employee function - No parameter required because is retrieving all employees
    fun getAllEmployees() = db.getEmployeeDao().getAllEmployees()

    // Search Employee function
    fun searchEmployee(query: String?) = db.getEmployeeDao().searchEmployee(query)

}
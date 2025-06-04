package au.edu.kbs.mobiledevelopment.employeeapp.repository

import au.edu.kbs.mobiledevelopment.employeeapp.database.EmployeeDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee


// A place where data will be contained
class EmployeeRepository(private val db: EmployeeDatabase) {
    // I'm calling the methods defined on EmployeeDao interface and I'll be using from here as functions

    // Insert All Employees method
    suspend fun insertAll(employees: List<Employee>) = db.getEmployeeDao().insertAll(employees)

    // Create Employee method
    suspend fun insertEmployee(employee: Employee) = db.getEmployeeDao().insertEmployee(employee)

    // Delete Employee method
    suspend fun deleteEmployee(employee: Employee) = db.getEmployeeDao().deleteEmployee(employee)

    // Update Employee method
    suspend fun updateEmployee(employee: Employee) = db.getEmployeeDao().updateEmployee(employee)

    // Get All Employee method - No parameter required because is retrieving all employees
    fun getAllEmployees() = db.getEmployeeDao().getAllEmployees()

    // Get total number of employees
    suspend fun getEmployeeCount() = db.getEmployeeDao().getEmployeeCount()

    // Search Employee method
    fun searchEmployee(query: String?) = db.getEmployeeDao().searchEmployee(query)

}
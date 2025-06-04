package au.edu.kbs.mobiledevelopment.employeeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(app: Application, private val employeeRepository: EmployeeRepository): AndroidViewModel(app) {
    // This acts as the bridge between the view and the model

    fun addEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.insertEmployee(employee)
        }

    fun updateEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.updateEmployee(employee)
        }

    fun deleteEmployee(employee: Employee) =
        viewModelScope.launch {
            employeeRepository.deleteEmployee(employee)
        }

    //fun getAllEmployees() = employeeRepository.getAllEmployees()
    val allEmployees = employeeRepository.getAllEmployees()

    // fun getEmployeeCount() to display the total number of employees on the database
    fun getEmployeeCount(onResult: (Int) -> Unit) {
        viewModelScope.launch {
            val count = employeeRepository.getEmployeeCount()
            onResult(count)
        }
    }

    fun searchEmployee(query: String?) =
        employeeRepository.searchEmployee(query)

}
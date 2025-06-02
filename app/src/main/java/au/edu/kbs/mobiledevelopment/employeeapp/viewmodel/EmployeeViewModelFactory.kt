package au.edu.kbs.mobiledevelopment.employeeapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.edu.kbs.mobiledevelopment.employeeapp.repository.EmployeeRepository

// this class instantiate and return view model
class EmployeeViewModelFactory(val app: Application, private val employeeRepository: EmployeeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(app, employeeRepository) as T
    }
}
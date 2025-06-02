package au.edu.kbs.mobiledevelopment.employeeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import au.edu.kbs.mobiledevelopment.employeeapp.database.EmployeeDatabase
import au.edu.kbs.mobiledevelopment.employeeapp.repository.EmployeeRepository
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var employeeViewModel: EmployeeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        }

    private fun setupViewModel(){
        val employeeRepository = EmployeeRepository(EmployeeDatabase(this))
        val viewModelProviderFactory = EmployeeViewModelFactory(application, employeeRepository)
        employeeViewModel = ViewModelProvider(this, viewModelProviderFactory)[EmployeeViewModel::class.java]
    }
}

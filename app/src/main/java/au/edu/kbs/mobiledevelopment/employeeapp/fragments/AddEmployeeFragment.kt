package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentAddEmployeeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel


class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee) {

    private var addEmployeeBinding: FragmentAddEmployeeBinding? = null
    private val binding get() = addEmployeeBinding!!

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var addEmployeeView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addEmployeeBinding = FragmentAddEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup toolbar with back btn
        val toolbar = binding.addToolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // to initialize the view model & the view itself
        employeeViewModel = (activity as MainActivity).employeeViewModel
        addEmployeeView = view

        binding.addEmployeeSaveBtn.setOnClickListener{
            saveEmployee(addEmployeeView)
        }
    }

    private fun saveEmployee(view: View){
        // calculate the initials when getting from user input
        fun getInitials(firstName: String?, lastName: String?): String {
            var initials = ""
            if (!firstName.isNullOrEmpty()) {
                initials += firstName.take(1)
            }
            if (!lastName.isNullOrEmpty()) {
                initials += lastName.take(1)
            }
            return initials
        }


        // Taking user inputs
        val firstName = binding.employeeFirstName.text.toString().trim()
        val lastName = binding.employeeLastName.text.toString().trim()
        val jobRole = binding.employeeJobRole.text.toString().trim()
        // Extended user inputs
        val department = binding.employeeDepartment.text.toString().trim()
        val hireDate = binding.employeeHireDate.text.toString().trim()
        val contractType = binding.employeeContractType.text.toString().trim()
        val salary = binding.employeeSalary.text.toString().trim()
        val email = binding.employeeEmail.text.toString().trim()
        val phoneNumber = binding.employeePhoneNumber.text.toString().trim()
        val address = binding.employeeAddress.text.toString().trim()
        val city = binding.employeeCity.text.toString().trim()
        val state = binding.employeeState.text.toString().trim()
        val zipCode = binding.employeeZipCode.text.toString().trim()
        val country = binding.employeeCountry.text.toString().trim()

        //Initials are manipulated but not asked to the user
        val initials = getInitials(firstName, lastName)

        // Helper Text to display error messages

        // firstName.isNotEmpty() && lastName.isNotEmpty() && jobRole.isNotEmpty() && department.isNotEmpty() && hireDate.isNotEmpty() && hireDate.isNotEmpty()
        if (firstName.isNotEmpty() && salary.isNotEmpty()){
            // if details are input, then save them to the database
            val salary = salary.toDouble() // Double type
            val employee = Employee(0, firstName, lastName, jobRole, initials, phoneNumber, email, department, hireDate, contractType, salary, address, city, state, zipCode, country)
            employeeViewModel.addEmployee(employee)

            // display a successful message and navigate back to the main screen
            Toast.makeText(addEmployeeView.context, "Employee added", Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            // not all details are completed, then show message
            Toast.makeText(addEmployeeView.context, "Please enter all details", Toast.LENGTH_LONG).show()
            binding.employeeFirstNameHelperText.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        addEmployeeBinding = null
    }

}
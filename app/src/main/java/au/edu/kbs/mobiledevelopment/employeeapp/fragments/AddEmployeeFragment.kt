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
            if (firstName != null && firstName.isNotEmpty()) {
                initials += firstName.take(1)
            }
            if (lastName != null && lastName.isNotEmpty()) {
                initials += lastName.take(1)
            }
            return initials
        }


        // Taking user inputs
        val firstName = binding.employeeFirstName.text.toString().trim()
        val lastName = binding.employeeLastName.text.toString().trim()
        val jobRole = binding.employeeJobRole.text.toString().trim()

        //todo: values to add
        val imgReference = "aaa"
        val initials = getInitials(firstName, lastName)
        val phoneNumber = "00"
        val email = "a@e.com"
        val department = ""
        val hireDate = ""
        val contractType = ""
        val salary = 000.00
        val address = ""
        val city = ""
        val state = ""
        val zipCode = ""
        val country = "Australia"


        if (firstName.isNotEmpty() && lastName.isNotEmpty() && jobRole.isNotEmpty()){
            // if details are input, then save them to the database
            val employee = Employee(0, firstName, lastName, jobRole, imgReference, initials, phoneNumber, email, department, hireDate, contractType, salary, address, city, state, zipCode, country)
            employeeViewModel.addEmployee(employee)

            // display a successful message and navigate back to the main screen
            Toast.makeText(addEmployeeView.context, "Employee added", Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            // not all details are completed, then show message
            Toast.makeText(addEmployeeView.context, "Please enter employee's details", Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        addEmployeeBinding = null
    }

}
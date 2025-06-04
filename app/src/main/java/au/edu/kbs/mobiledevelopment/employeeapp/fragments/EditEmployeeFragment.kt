package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentEditEmployeeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel

class EditEmployeeFragment : Fragment(R.layout.fragment_edit_employee) {

    private var editEmployeeBinding: FragmentEditEmployeeBinding? = null
    private val binding get() = editEmployeeBinding!!

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var currentEmployee: Employee

    // As we have arguments attached to this fragment through nav graph
    private val args: EditEmployeeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editEmployeeBinding = FragmentEditEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)


        // Hide the global bar if it's showing
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // Setup toolbar with back btn
        val toolbar = binding.editToolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        // to initialize the view model & the view itself
        employeeViewModel = (activity as MainActivity).employeeViewModel
        currentEmployee = args.employee!!

        binding.editEmployeeFirstName.setText(currentEmployee.firstName)
        binding.editEmployeeLastName.setText(currentEmployee.lastName)
        binding.editEmployeeJobRole.setText(currentEmployee.jobRole)

        binding.editEmployeeSaveBtn.setOnClickListener{
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
            val firstName = binding.editEmployeeFirstName.text.toString().trim()
            val lastName = binding.editEmployeeLastName.text.toString().trim()
            val jobRole = binding.editEmployeeJobRole.text.toString().trim()

            //todo: values to add

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
                val employee = Employee(currentEmployee.id, firstName, lastName, jobRole, initials, phoneNumber, email, department, hireDate, contractType, salary, address, city, state, zipCode, country)
                employeeViewModel.updateEmployee(employee)

                // display a successful message and navigate back to the main screen
                Toast.makeText(context, "Employee updated", Toast.LENGTH_LONG).show()
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                // not all details are completed, then show message
                Toast.makeText(context, "Please enter employee's details", Toast.LENGTH_LONG).show()
            }
        }

        binding.editEmployeeDeleteBtn.setOnClickListener{
            deleteEmployee()
        }
    }

    // Delete employee
    private fun deleteEmployee(){
        AlertDialog.Builder(activity).apply{
            setTitle("Delete Employee")
            setMessage("Do you want to delete this employee?")
            setPositiveButton("Delete") {_,_ ->
                employeeViewModel.deleteEmployee(currentEmployee)
                Toast.makeText(context, "Employee has been deleted", Toast.LENGTH_LONG).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }



    override fun onDestroy() {
        super.onDestroy()
        editEmployeeBinding = null
    }
}
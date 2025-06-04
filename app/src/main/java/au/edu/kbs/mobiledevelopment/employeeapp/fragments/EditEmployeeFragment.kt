package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        // Dropdown for Department Options
        val departmentDropdown = listOf("Admin", "Finance", "HR", "IT", "Marketing", "Sales")
        val departmentAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            departmentDropdown
        )

        binding.editEmployeeDepartment.setAdapter(departmentAdapter)
        binding.editEmployeeDepartment.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editEmployeeDepartment.showDropDown()
            }
        }

        // Dropdown for Job role Options
        val jobRoleDropdown = listOf("Customer Service Rep",
            "Delivery Driver",
            "Finance Assistant",
            "HR Analyst",
            "HR Manager",
            "Inventory Analyst",
            "IT Analyst",
            "IT Manager",
            "IT Support Technician",
            "Logistics Coordinator",
            "Logistics Coordinator",
            "MKT Analyst",
            "Sales Associate",
            "Store Manager",
            "Warehouse Driver",
            "Warehouse Worker",)
        val jobRoleAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            jobRoleDropdown
        )

        binding.editEmployeeJobRole.setAdapter(jobRoleAdapter)
        binding.editEmployeeJobRole.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editEmployeeJobRole.showDropDown()
            }
        }

        // Dropdown for Contract Type Options
        val contractTypeDropdown = listOf("Full-time", "Part-time", "Casual", "Contract")
        val contractTypeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            contractTypeDropdown
        )

        binding.editEmployeeContractType.setAdapter(contractTypeAdapter)
        binding.editEmployeeContractType.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editEmployeeContractType.showDropDown()
            }
        }

        // Date Picker from MaterialDatePicker UI Component for Hire Date
        // Grabbing UI element from fragment .xml
        val hireDate = binding.editEmployeeHireDate
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Hiring Date")
            .build()

        // To display the picker when user taps the input field
        hireDate.setOnClickListener{
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        // To handle when user selects the date from the picker
        datePicker.addOnPositiveButtonClickListener { selection ->
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val formattedDate = sdf.format(Date(selection))
            hireDate.setText(formattedDate)
        } // When typing the date, the pattern is MM-dd-yyyy but I couldn't change it.

        // Checks if at least 1 input has an update (value different from the one pulled from the database) to show an Alert Dialog when back btn tapped
        fun checkForChangesBeforeLeaving(currentEmployee: Employee) {
            val hasChanges = listOf(
                binding.editEmployeeFirstName.text.toString() != currentEmployee.firstName,
                binding.editEmployeeLastName.text.toString() != currentEmployee.lastName,
                binding.editEmployeeJobRole.text.toString() != currentEmployee.jobRole,
                binding.editEmployeeDepartment.text.toString() != currentEmployee.department,
                binding.editEmployeeHireDate.text.toString() != currentEmployee.hireDate,
                binding.editEmployeeContractType.text.toString() != currentEmployee.contractType,
                binding.editEmployeeSalary.text.toString() != currentEmployee.salary.toString(),
                binding.editEmployeeEmail.text.toString() != currentEmployee.email,
                binding.editEmployeePhoneNumber.text.toString() != currentEmployee.phoneNumber,
                binding.editEmployeeAddress.text.toString() != currentEmployee.address,
                binding.editEmployeeCity.text.toString() != currentEmployee.city,
                binding.editEmployeeState.text.toString() != currentEmployee.state,
                binding.editEmployeeZipCode.text.toString() != currentEmployee.zipCode,
                binding.editEmployeeCountry.text.toString() != currentEmployee.country
            ).any { it }

            if (hasChanges) {
                AlertDialog.Builder(activity).apply {
                    setTitle("Unsaved Changes")
                    setMessage("You've made changes. Are you sure you want to leave?")
                    setPositiveButton("Leave") { _, _ ->
                        view.findNavController().popBackStack(R.id.homeFragment, false)
                    }
                    setNegativeButton("Stay", null)
                }.create().show()
            } else {
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }
        }

        // Setup toolbar with back btn
        val toolbar = binding.editToolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            checkForChangesBeforeLeaving(currentEmployee)
        }

        // Set Cancel btn to Stay or Leave
        val cancelBtn = binding.editEmployeeCancelBtn
        cancelBtn.setOnClickListener {
            checkForChangesBeforeLeaving(currentEmployee)
        }

        // to initialize the view model & the view itself
        employeeViewModel = (activity as MainActivity).employeeViewModel
        currentEmployee = args.employee!!

        // Displaying current employee's details from the database
        binding.editEmployeeFirstName.setText(currentEmployee.firstName)
        binding.editEmployeeLastName.setText(currentEmployee.lastName)
        binding.editEmployeeJobRole.setText(currentEmployee.jobRole)
        binding.editEmployeeDepartment.setText(currentEmployee.department)
        binding.editEmployeeHireDate.setText(currentEmployee.hireDate)
        binding.editEmployeeContractType.setText(currentEmployee.contractType)
        binding.editEmployeeSalary.setText(currentEmployee.salary.toString())
        binding.editEmployeeEmail.setText(currentEmployee.email)
        binding.editEmployeePhoneNumber.setText(currentEmployee.phoneNumber)
        binding.editEmployeeAddress.setText(currentEmployee.address)
        binding.editEmployeeCity.setText(currentEmployee.city)
        binding.editEmployeeState.setText(currentEmployee.state)
        binding.editEmployeeZipCode.setText(currentEmployee.zipCode)
        binding.editEmployeeCountry.setText(currentEmployee.country)

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

            // Taking user inputs for UPDATING
            val firstName = binding.editEmployeeFirstName.text.toString().trim()
            val lastName = binding.editEmployeeLastName.text.toString().trim()
            val jobRole = binding.editEmployeeJobRole.text.toString().trim()
            val department = binding.editEmployeeDepartment.text.toString().trim()
            val hireDate = binding.editEmployeeHireDate.text.toString().trim()
            val contractType = binding.editEmployeeContractType.text.toString().trim()
            val salary = binding.editEmployeeSalary.text.toString().trim()
            val email = binding.editEmployeeEmail.text.toString().trim()
            val phoneNumber = binding.editEmployeePhoneNumber.text.toString().trim()
            val address = binding.editEmployeeAddress.text.toString().trim()
            val city = binding.editEmployeeCity.text.toString().trim()
            val state = binding.editEmployeeState.text.toString().trim()
            val zipCode = binding.editEmployeeZipCode.text.toString().trim()
            val country = binding.editEmployeeCountry.text.toString().trim()

            //Initials are manipulated but not asked to the user
            val initials = getInitials(firstName, lastName)

            // IF user removes value on input fields --> each case handle with helper text displayed at input field level
            fun firstName(): Unit? {
                return if (firstName.isNotEmpty()) {
                    binding.employeeFirstNameHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeFirstNameHelperText.visibility = View.VISIBLE
                    null
                }
            }

            fun lastName(): Unit? {
                return if (lastName.isNotEmpty()) {
                    binding.employeeLastNameHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeLastNameHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun jobRole(): Unit? {
                return if (jobRole.isNotEmpty()) {
                    binding.employeeJobRoleHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeJobRoleHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun department(): Unit? {
                return if (department.isNotEmpty()) {
                    binding.employeeDepartmentHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeDepartmentHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun hireDate(): Unit? {
                return if (hireDate.isNotEmpty()) {
                    binding.employeeHireDateHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeHireDateHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun contractType(): Unit? {
                return if (contractType.isNotEmpty()) {
                    binding.employeeContractTypeHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeContractTypeHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun salary(): Unit? {
                return if (salary.isNotEmpty()) {
                    binding.employeeSalaryHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeSalaryHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun email() {
                return if (email.isEmpty()) {
                    binding.employeeEmailHelperText.visibility = View.VISIBLE
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    // Checking email structure 'aa@aa.aa'
                    binding.employeeEmailHelperText.setText(R.string.emailFormat)
                    binding.employeeEmailHelperText.visibility = View.VISIBLE
                } else {
                    binding.employeeEmailHelperText.visibility = View.INVISIBLE
                }
            }

            fun phoneNumber(): Unit? {
                return if (phoneNumber.isNotEmpty()) {
                    binding.employeePhoneNumberHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeePhoneNumberHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun city(): Unit? {
                return if (city.isNotEmpty()) {
                    binding.employeeCityHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeCityHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun address(): Unit? {
                return if (address.isNotEmpty()) {
                    binding.employeeAddressHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeAddressHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun state(): Unit? {
                return if (state.isNotEmpty()) {
                    binding.employeeStateHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeStateHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun zipCode(): Unit? {
                return if (zipCode.isNotEmpty()) {
                    binding.employeeZipCodeHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeZipCodeHelperText.visibility = View.VISIBLE
                    null
                }
            }
            fun country(): Unit? {
                return if (country.isNotEmpty()) {
                    binding.employeeCountryHelperText.visibility = View.INVISIBLE
                } else {
                    binding.employeeCountryHelperText.visibility = View.VISIBLE
                    null
                }
            }


            // Time to check input fields from user's inputs & wrap all as -current- Employee object
            if (
                firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                jobRole.isNotEmpty() &&
                department.isNotEmpty() &&
                hireDate.isNotEmpty() &&
                contractType.isNotEmpty() &&
                salary.isNotEmpty()  &&
                email.isNotEmpty() &&
                phoneNumber.isNotEmpty() &&
                city.isNotEmpty() &&
                address.isNotEmpty() &&
                state.isNotEmpty() &&
                zipCode.isNotEmpty() &&
                country.isNotEmpty()
            ){
                // if details are input, then save them to the database
                val salary = salary.toDouble() // Double type
                val employee = Employee(currentEmployee.id, firstName, lastName, jobRole, initials, phoneNumber, email, department, hireDate, contractType, salary, address, city, state, zipCode, country)
                employeeViewModel.updateEmployee(employee)

                // display a successful message and navigate back to the main screen
                Toast.makeText(context, "Employee updated", Toast.LENGTH_LONG).show()
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                // not all details are completed, then show general message at the bottom
                Toast.makeText(context, "Please enter all employee's details with valid data", Toast.LENGTH_LONG).show()
                // checking which input field was incomplete and show message at input field level
                firstName()
                lastName()
                jobRole()
                department()
                hireDate()
                contractType()
                salary()
                email()
                phoneNumber()
                city()
                address()
                state()
                zipCode()
                country()
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
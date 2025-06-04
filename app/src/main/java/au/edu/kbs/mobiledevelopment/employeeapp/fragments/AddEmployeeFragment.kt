package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentAddEmployeeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*



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

        // Dropdown for Department Options
        val departmentDropdown = listOf("Admin", "Finance", "HR", "IT", "Marketing", "Sales")
        val departmentAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            departmentDropdown
        )

        binding.employeeDepartment.setAdapter(departmentAdapter)
        binding.employeeDepartment.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.employeeDepartment.showDropDown()
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

        binding.employeeJobRole.setAdapter(jobRoleAdapter)
        binding.employeeJobRole.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.employeeJobRole.showDropDown()
            }
        }

        // Dropdown for Contract Type Options
        val contractTypeDropdown = listOf("Full-time", "Part-time", "Casual", "Contract")
        val contractTypeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            contractTypeDropdown
        )

        binding.employeeContractType.setAdapter(contractTypeAdapter)
        binding.employeeContractType.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.employeeContractType.showDropDown()
            }
        }

        // Date Picker from MaterialDatePicker UI Component for Hire Date
        // Grabbing UI element from fragment .xml
        val hireDate = binding.employeeHireDate
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


        // Checks if at least 1 input has a value to show an Alert Dialog when back btn tapped
        fun checkForChangesBeforeLeaving() {
            val hasAnyInput = listOf(
                binding.employeeFirstName.text.toString(),
                binding.employeeLastName.text.toString(),
                binding.employeeJobRole.text.toString(),
                binding.employeeDepartment.text.toString(),
                binding.employeeHireDate.text.toString(),
                binding.employeeContractType.text.toString(),
                binding.employeeSalary.text.toString(),
                binding.employeeEmail.text.toString(),
                binding.employeePhoneNumber.text.toString(),
                binding.employeeAddress.text.toString(),
                binding.employeeCity.text.toString(),
                binding.employeeState.text.toString(),
                binding.employeeZipCode.text.toString(),
                binding.employeeCountry.text.toString()
            ).any { it.isNotEmpty() }

            if (hasAnyInput) {
                AlertDialog.Builder(activity).apply {
                    setTitle("Unsaved Changes")
                    setMessage("You may have unsaved changes. Are you sure you want to  leave?")
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
        val toolbar = binding.addToolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        // Calling function to check inputs when tapping on toolbar nav (back btn) -> show confirmation message to leave
        toolbar.setNavigationOnClickListener {
            checkForChangesBeforeLeaving()
        }

        // Set Cancel btn to Stay or Leave
        val cancelBtn = binding.addEmployeeCancelBtn
        cancelBtn.setOnClickListener {
            checkForChangesBeforeLeaving()
        }


        // to initialize the view model & the view itself
        employeeViewModel = (activity as MainActivity).employeeViewModel
        addEmployeeView = view

        binding.addEmployeeSaveBtn.setOnClickListener{
            saveEmployee(addEmployeeView)
        }
    }


    // Save Employee Detail Function
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

        // User input validation: each case handle with helper text displayed at input field level
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


        // Time to check input fields from user's inputs & wrap all as an Employee object
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
            val employee = Employee(0, firstName, lastName, jobRole, initials, phoneNumber, email, department, hireDate, contractType, salary, address, city, state, zipCode, country)
            employeeViewModel.addEmployee(employee)

            // display a successful message and navigate back to the main screen
            Toast.makeText(addEmployeeView.context, "Employee added", Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            // not all details are completed, then show general message at the bottom
            Toast.makeText(addEmployeeView.context, "Please enter all fields required with valid data", Toast.LENGTH_LONG).show()
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


    override fun onDestroy() {
        super.onDestroy()
        addEmployeeBinding = null
    }

}
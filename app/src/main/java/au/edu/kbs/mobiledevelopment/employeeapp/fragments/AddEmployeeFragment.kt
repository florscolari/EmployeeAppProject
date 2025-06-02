package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentAddEmployeeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee


class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee), MenuProvider {

    private var addEmployeeBinding: FragmentAddEmployeeBinding? = null
    private val binding get() = addEmployeeBinding!!

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var addEmployeeView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addEmployeeBinding = FragmentAddEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to initialize the menu
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // to initialize the view model & the view itself
        employeeViewModel = (activity as MainActivity).employeeViewModel
        addEmployeeView = view
    }

    private fun saveEmployee(view: View){
        // Taking user inputs
        val firstName = binding.employeeFirstName.text.toString().trim()
        val lastName = binding.employeeLastName.text.toString().trim()
        val jobRole = binding.editEmployeeJobRole.text.toString().trim()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && jobRole.isNotEmpty()){
            // if details are input, then save them to the database
            val employee = Employee(0, firstName, lastName, jobRole)
            employeeViewModel.addEmployee(employee)

            // display a successful message and navigate back to the main screen
            Toast.makeText(addEmployeeView.context, "Employee added", Toast.LENGTH_LONG).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            // not all details are completed, then show message
            Toast.makeText(addEmployeeView.context, "Please enter employee's details", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Clear default menu
        menu.clear()
        // Attach the menu created for this fragment
        menuInflater.inflate(R.menu.menu_add_employee, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu -> {
                saveEmployee(addEmployeeView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addEmployeeBinding = null
    }

}
package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.adapter.EmployeeAdapter
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentHomeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    // declarations for view model to connect with UI & adapter for recycler view
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeAdapter: EmployeeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        // to initialize the menu
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // to initialize the view model
        employeeViewModel = (activity as MainActivity).employeeViewModel

        // set navigation from add employee fab btn (home fragment → addEmployee fragment)
        binding.addEmployeeFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addEmployeeFragment)
        }
    }

    // A set of functions

    // To update the UI: switches between empty state (image) & list of employees when there is at least 1 employee to be listed.
    private fun updateUI(employee: List<Employee>?){
        if (employee != null){
            if (employee.isEmpty()){
                binding.emptyEmployeesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
            } else {
                binding.emptyEmployeesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    // Recycler View function
    private fun setupHomeRecyclerView(){
       employeeAdapter = EmployeeAdapter()
       binding.homeRecyclerView.apply{
           layoutManager = LinearLayoutManager(context)
           setHasFixedSize(true)
           adapter = employeeAdapter
       }

       // to display all employees the view model must be observed
       activity?.let {
           employeeViewModel.getAllEmployees().observe(viewLifecycleOwner){
               employee -> employeeAdapter.differ.submitList(employee)
               updateUI(employee)
           }
       }
    }


}
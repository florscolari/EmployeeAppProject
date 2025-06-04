package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.kbs.mobiledevelopment.employeeapp.MainActivity
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.adapter.EmployeeAdapter
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentHomeBinding
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee
import au.edu.kbs.mobiledevelopment.employeeapp.viewmodel.EmployeeViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    // declarations for view model to connect with UI & adapter for recycler view
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var employeeAdapter: EmployeeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)


        // search feature
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchEmployee(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchEmployee(newText.orEmpty())
                return true
            }
        })


        // to initialize the view model
        employeeViewModel = (activity as MainActivity).employeeViewModel
        setupHomeRecyclerView()

        // set navigation from add employee fab btn (home fragment → addEmployee fragment)
        binding.addEmployeeFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addEmployeeFragment)
        }
        // set total employee counter
        employeeViewModel.getEmployeeCount { count ->
            binding.employeeTotalNumber.text = count.toString()

        }
    }

    // To update the UI: switches between empty state (image) & list of employees when there is at least 1 employee to be listed.
    private fun updateUI(employee: List<Employee>?){
        if (employee.isNullOrEmpty()){
                binding.emptyEmployeesImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            } else {
                binding.emptyEmployeesImage.visibility = View.GONE
                binding.homeRecyclerView.visibility = View.VISIBLE
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
           employeeViewModel.allEmployees.observe(viewLifecycleOwner){
               employees -> employeeAdapter.differ.submitList(employees)
               updateUI(employees)
           }
       }
    }

    private fun searchEmployee(query: String) {
        val formattedQuery = "%${query.trim()}%"
        employeeViewModel.searchEmployee(formattedQuery).observe(viewLifecycleOwner) { employees ->
            employeeAdapter.differ.submitList(employees)
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Set to show results to the users as they are typing on the search bar
        if (newText != null){
            searchEmployee(newText)
        }
        return true
    }

    override fun onDestroy(){
        super.onDestroy()
        homeBinding = null
    }

    /*override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        //todo: check if there's an issue by passing Int (id) and String (names)
        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        // to use the search bar as filter, no need to tap on a btn to run the search
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)

    }*/


}
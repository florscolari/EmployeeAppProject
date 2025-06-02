package au.edu.kbs.mobiledevelopment.employeeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.EmployeeItemBinding
import au.edu.kbs.mobiledevelopment.employeeapp.fragments.HomeFragmentDirections
import au.edu.kbs.mobiledevelopment.employeeapp.model.Employee

// An adapter & a viewHolder are needed to attach data with its respective UI
class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    // from now on I can use itemBinding to access to all UI elements presents on employee_item.xml
    class EmployeeViewHolder(val itemBinding: EmployeeItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Employee>(){
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean{
            return oldItem.id == newItem.id &&
                    oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    // Implementing 3 members of EmployeeAdapter Class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        // Define the view, means the UI for employee_item
        return EmployeeViewHolder(
            EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = differ.currentList[position]

        // I'm linking UI elements with data to be displayed
        //TODO:If I want to display another attribute in the list, I need to come back here and adjust it
        holder.itemBinding.employeeFirstName.text = currentEmployee.firstName
        holder.itemBinding.employeeLastName.text = currentEmployee.lastName
        holder.itemBinding.employeeJobRole.text = currentEmployee.jobRole

        // Setting to click on an item and display the Employee details
        holder.itemView.setOnClickListener(){
            val direction = HomeFragmentDirections.actionHomeFragmentToEditEmployeeFragment(currentEmployee)
            it.findNavController().navigate(direction)
        }
    }
}
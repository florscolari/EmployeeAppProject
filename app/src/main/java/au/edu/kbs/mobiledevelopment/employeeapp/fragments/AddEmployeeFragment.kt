package au.edu.kbs.mobiledevelopment.employeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import au.edu.kbs.mobiledevelopment.employeeapp.R
import au.edu.kbs.mobiledevelopment.employeeapp.databinding.FragmentAddEmployeeBinding

class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee), MenuProvider {

    private var addEmployeeBinding: FragmentAddEmployeeBinding? = null
    private val binding get() = addEmployeeBinding!!
    //to continue from here 1:02:35

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_employee, container, false)
    }


}
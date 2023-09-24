package com.example.tasks_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasks_app.databinding.FragmentEditTaskBinding


class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        //val textView = view.findViewById<TextView>(R.id.task_id)
        val view = binding.root

        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        //EditTaskViewModelFactory
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        //refere to viewmodel
        val viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditTaskViewModel::class.java)

        //set the layout data binding variable
        binding.viewModel = viewModel

        //this let the layout interact with the live data properties
        binding.lifecycleOwner = viewLifecycleOwner

        //Naviagte to TaskFragment when the navigate to List Proeprty is
        //set to true, and call the view model's on NavigatedToList method
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigateToList()
            }
        })


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
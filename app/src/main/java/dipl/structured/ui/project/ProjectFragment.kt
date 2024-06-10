package dipl.structured.ui.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dipl.structured.R
import dipl.structured.TasksApplication
import dipl.structured.data.ProjectListAdapter
import dipl.structured.data.TaskListAdapter
import dipl.structured.databinding.FragmentProjectBinding
import dipl.structured.ui.task.TaskViewModel
import dipl.structured.ui.task.TaskViewModelFactory

class ProjectFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val projectViewModel:ProjectViewModel by viewModels { ProjectViewModelFactory((requireActivity().application as TasksApplication).taskRepo,(requireActivity().application as TasksApplication).applicationScope)}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProjectBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView=root.findViewById<RecyclerView>(R.id.recyclerview)
        val Adapter= TaskListAdapter()
        Log.d("recyclerView init", "onCreateView triggered, recyclerView "+recyclerView.toString()+" and adapter "+Adapter.toString())
        recyclerView.adapter=Adapter
        recyclerView.layoutManager=LinearLayoutManager(requireActivity())
        projectViewModel.availableTasks!!.observe(viewLifecycleOwner) {tasks ->
            tasks.let {Adapter.submitList(it)
                Log.d("Project adapter event","projects updated, count: "+it.size)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
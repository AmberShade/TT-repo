package dipl.structured.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dipl.structured.R
import dipl.structured.TasksApplication
import dipl.structured.data.ProjectListAdapter
import dipl.structured.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory((requireActivity().application as TasksApplication).projectRepo,(requireActivity().application as TasksApplication).applicationScope) }
class HomeBackCallback(private val nav:NavController):OnBackPressedCallback(true){

    @SuppressLint("RestrictedApi")
    override fun handleOnBackPressed(){
        if (nav.currentBackStackEntry!!.destination==nav.findDestination(R.id.loginFragment)){
        val args=Bundle()
        args.putBoolean("logout",true)
        nav.navigate(R.id.action_global_loginFragment,args)}
        else {
            nav.popBackStack()
        }
    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val back = requireActivity().onBackPressedDispatcher.addCallback(this,HomeBackCallback(findNavController()))

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recycler=root.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter= ProjectListAdapter()
        Log.d("recyclerView init", "onCreateView triggered, recyclerView "+recycler.toString()+" and adapter "+adapter.toString())
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(requireActivity())
        homeViewModel.availableProjects!!.observe(viewLifecycleOwner) {
                projects->projects.let{adapter.submitList(it)
            Log.d("Project adapter event","projects updated, count: "+it.size)}
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
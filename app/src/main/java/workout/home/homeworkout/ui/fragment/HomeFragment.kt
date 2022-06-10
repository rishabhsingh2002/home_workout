package workout.home.homeworkout.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        // Navigate to abs fragment
        binding.cvAbs.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_miHome_to_absFragment)
        }
        binding.cvChest.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_miHome_to_chestFragment)
        }
        binding.cvArms.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_miHome_to_armsFragment)
        }
        binding.cvBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_miHome_to_backFragment)
        }
        binding.cvLeg.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_miHome_to_legFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
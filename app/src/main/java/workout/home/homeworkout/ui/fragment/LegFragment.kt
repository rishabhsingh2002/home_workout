package workout.home.homeworkout.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import workout.home.homeworkout.R
import workout.home.homeworkout.adapter.ExerciseAdapter
import workout.home.homeworkout.databinding.FragmentLegBinding
import workout.home.homeworkout.model.ExerciseModel

class LegFragment : Fragment() {

    private var _binding: FragmentLegBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLegBinding.inflate(inflater, container, false)


        //Navigating to AbsWorkout Fragment
        binding.btnStartLegExercise.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_legFragment_to_legExerciseFragment)
        }

        //Function to setup the recyclerview
        fireBaseSetUpWithRecyclerview()
        //Toolbar Setup
        toolBarSetUp()


        return binding.root
    }

    private fun fireBaseSetUpWithRecyclerview() {
        db = FirebaseFirestore.getInstance()
        db.collection("leg").addSnapshotListener { value, error ->

            val listExerciseData = arrayListOf<ExerciseModel>()
            val data = value?.toObjects(ExerciseModel::class.java)
            listExerciseData.addAll(data!!)
            listExerciseData.sortBy {
                it.id
            }
            binding.rvLegWorkout.layoutManager = LinearLayoutManager(context)
            binding.rvLegWorkout.adapter =
                ExerciseAdapter(requireContext(), listExerciseData)
        }
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLegFragment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            binding.toolbarLegFragment.title = "Leg Workout"
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.toolbarLegFragment.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
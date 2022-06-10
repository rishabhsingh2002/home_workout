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
import workout.home.homeworkout.databinding.FragmentChestBinding
import workout.home.homeworkout.model.ExerciseModel

class ChestFragment : Fragment() {
    private var _binding: FragmentChestBinding? = null

    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChestBinding.inflate(inflater, container, false)


        //Navigating to AbsWorkout Fragment
        binding.btnStartChestExercise.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_chestFragment_to_chestExerciseFragment)
        }

        //Function to setup the recyclerview
        fireBaseSetUpWithRecyclerview()
        //Toolbar Setup
        toolBarSetUp()


        return binding.root
    }

    private fun fireBaseSetUpWithRecyclerview() {
        db = FirebaseFirestore.getInstance()
        db.collection("chest").addSnapshotListener { value, error ->

            val listExerciseData = arrayListOf<ExerciseModel>()
            val data = value?.toObjects(ExerciseModel::class.java)
            listExerciseData.addAll(data!!)
            listExerciseData.sortBy {
                it.id
            }
            binding.rvChestWorkout.layoutManager = LinearLayoutManager(context)
            binding.rvChestWorkout.adapter =
                ExerciseAdapter(requireContext(), listExerciseData)
        }
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarChestFragment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            binding.toolbarChestFragment.title = "Chest Workout"
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.toolbarChestFragment.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
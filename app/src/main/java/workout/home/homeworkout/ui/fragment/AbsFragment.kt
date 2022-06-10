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
import workout.home.homeworkout.databinding.FragmentAbsBinding
import workout.home.homeworkout.model.ExerciseModel

class AbsFragment : Fragment() {
    private var _binding: FragmentAbsBinding? = null
    private lateinit var db: FirebaseFirestore

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAbsBinding.inflate(inflater, container, false)


        //Navigating to AbsWorkout Fragment
        binding.btnStartAbsExercise.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_absFragment_to_absExerciseFragment)
        }

        //Function to setup the recyclerview
        fireBaseSetUpWithRecyclerview()
        //Toolbar Setup
        toolBarSetUp()


        return binding.root
    }

    private fun fireBaseSetUpWithRecyclerview() {
        // Firestore Setup
        db = FirebaseFirestore.getInstance()
        db.collection("abs").addSnapshotListener { value, error ->

            val listExerciseData = arrayListOf<ExerciseModel>()
            val data = value?.toObjects(ExerciseModel::class.java)
            listExerciseData.addAll(data!!)
            listExerciseData.sortBy {
                it.id
            }
            binding.rvAbsWorkout.layoutManager = LinearLayoutManager(context)
            binding.rvAbsWorkout.adapter =
                ExerciseAdapter(requireContext(), listExerciseData)
        }


    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarAbsFragment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            binding.toolbarAbsFragment.title = "Abs Workout"
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.toolbarAbsFragment.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
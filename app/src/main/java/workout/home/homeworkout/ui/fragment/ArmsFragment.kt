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
import workout.home.homeworkout.databinding.FragmentArmsBinding
import workout.home.homeworkout.model.ExerciseModel

class ArmsFragment : Fragment() {

    private var _binding: FragmentArmsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArmsBinding.inflate(inflater, container, false)


        //Navigating to AbsWorkout Fragment
        binding.btnStartArmsExercise.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_armsFragment_to_armsExerciseFragment)
        }

        //Function to setup the recyclerview
        fireBaseSetUpWithRecyclerview()
        //Toolbar Setup
        toolBarSetUp()

        return binding.root
    }

    private fun fireBaseSetUpWithRecyclerview() {
        db = FirebaseFirestore.getInstance()
        db.collection("arms").addSnapshotListener { value, error ->

            val listExerciseData = arrayListOf<ExerciseModel>()
            val data = value?.toObjects(ExerciseModel::class.java)
            listExerciseData.addAll(data!!)
            listExerciseData.sortBy {
                it.id
            }
            binding.rvArmsWorkout.layoutManager = LinearLayoutManager(context)
            binding.rvArmsWorkout.adapter =
                ExerciseAdapter(requireContext(), listExerciseData)
        }
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarArmsFragment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            binding.toolbarArmsFragment.title = "Arms Workout"
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        binding.toolbarArmsFragment.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
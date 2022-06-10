package workout.home.homeworkout.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentChestExerciseBinding
import workout.home.homeworkout.model.ExerciseModel
import java.util.*

class ChestExerciseFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentChestExerciseBinding? = null
    private val binding get() = _binding!!

    // Text to speech
    private var tts: TextToSpeech? = null

    //Rest and Exercise Timer Initialisation
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    //Model Class Initialisation
    private var listExerciseData: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChestExerciseBinding.inflate(inflater, container, false)


        //Toolbar Setup
        toolBarSetUp()

        //Back button pressed handle
        onBackButtonPressed()

        //Firestore Setup
        db = FirebaseFirestore.getInstance()
        db.collection("chest").addSnapshotListener { value, error ->
            listExerciseData = arrayListOf<ExerciseModel>()
            val data = value?.toObjects(ExerciseModel::class.java)
            //sorted the array
            listExerciseData!!.sortBy {
                it.id
            }
            listExerciseData!!.addAll(data!!)

            // Rest Timer Setup
            setUpRestView()

            // Text to speech
            tts = TextToSpeech(context, this@ChestExerciseFragment as TextToSpeech.OnInitListener?)
        }

        return binding.root
    }

    //Rest view
    private fun setUpRestView() {
        binding.constraintLayoutExercise.visibility = View.INVISIBLE
        binding.llChestRest.visibility = View.VISIBLE
        binding.llNextExercise.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        listExerciseData?.sortBy {
            it.id
        }
        binding.tvNextExercise.text = listExerciseData!![currentExercisePosition + 1].name
        Glide.with(requireContext()).asGif()
            .load(listExerciseData!![currentExercisePosition + 1].image)
            .into(binding.imgNextExercise)

        setRestProgressBar()
    }

    // for rest timer
    private fun setRestProgressBar() {
        restTimer = object : CountDownTimer(AbsExerciseFragment.REST_TIME, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding.tvTimeRest.text = (15 - restProgress).toString()
                binding.btnSkipChest.setOnClickListener {
                    restTimer?.cancel()
                    onFinish()
                }
            }

            override fun onFinish() {
                currentExercisePosition++
                listExerciseData!![currentExercisePosition].setIsSelected(true)

                setUpExerciseView()
            }
        }.start()

    }

    //Exercise Timer
    private fun setUpExerciseView() {
        binding.constraintLayoutExercise.visibility = View.VISIBLE
        binding.llChestRest.visibility = View.INVISIBLE
        binding.llNextExercise.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        listExerciseData?.sortBy {
            it.id
        }
        // Setup the text to speech
        speakOut(listExerciseData!![currentExercisePosition].name)

        // To set the data with the views
        Glide.with(requireContext()).asGif()
            .load(listExerciseData!![currentExercisePosition].image)
            .into(binding.imgChestExercise)
        binding.tvExercise.text = listExerciseData!![currentExercisePosition].name

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {

        exerciseTimer = object : CountDownTimer(AbsExerciseFragment.EXERCISE_TIME, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding.tvTime.text = (45 - exerciseProgress).toString()
                binding.btnNextExerciseChest.setOnClickListener {
                    exerciseTimer?.cancel()
                    onFinish()
                }
            }

            override fun onFinish() {
                if (currentExercisePosition < listExerciseData?.size!! - 1) {
                    listExerciseData!![currentExercisePosition].setIsSelected(false)
                    listExerciseData!![currentExercisePosition].setIsCompleted(true)

                    setUpRestView()
                } else {
                    // activity?.finish()
                    // Sent to finish activity
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_chestExerciseFragment_to_finishFragment)

                }
            }
        }.start()
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarChestDetailedWorkout)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarChestDetailedWorkout.title = "Chest Exercise"
        binding.toolbarChestDetailedWorkout.setNavigationOnClickListener {
// requireActivity().onBackPressed()
            customDialogForBackButton()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(
                Locale.US
            )

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "THE LANGUAGE SPECIFIED IS NOT SUPPORTED")
            }
        } else {
            Log.e("TTS", "INITIALIZATION FAILED")

        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun onBackButtonPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    customDialogForBackButton()
                }
            })
    }

    private fun customDialogForBackButton() {
        val builder = AlertDialog.Builder(context,R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_back_pressed_dialog, null)
        val button_yes = view.findViewById<Button>(R.id.btn_yes)
        val button_no = view.findViewById<Button>(R.id.btn_no)
        builder.setView(view)
        button_yes.setOnClickListener {
            findNavController().popBackStack()
            builder.dismiss()
        }
        button_no.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        // Shutting down the textToSpeech when activity is destroyed
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
    }

}
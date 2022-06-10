package workout.home.homeworkout.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    val BmiLink =
        "https://firebasestorage.googleapis.com/v0/b/home-workout-dddfd.appspot.com/o/bmi%2Fbmi_gif1.gif?alt=media&token=1f8ec221-d6d7-4d48-b01d-51581d45f61e"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        //Toolbar Setup
        toolBarSetUp()



        binding.btnCalculate.setOnClickListener {
            if (validateUnits()) {
                val heightValue: Float = binding.edtHeight.text.toString().toFloat() / 100
                val weightValue: Float = binding.edtWeight.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)

                customDialogForBackButton(bmi)

                binding.edtHeight.text?.clear()
                binding.edtWeight.text?.clear()

            } else {
                Toast.makeText(activity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }



        return binding.root
    }

    private fun customDialogForBackButton(bmi: Float) {
        val builder = android.app.AlertDialog.Builder(context, R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.bmi_dialog, null)
        val button_ok = view.findViewById<Button>(R.id.btn_ok)
        val imageView = view.findViewById<ImageView>(R.id.img_bmi_lottie)
        Glide.with(requireContext()).asGif().load(BmiLink).into(imageView)
        val tvBmi = view.findViewById<TextView>(R.id.tv_bmi)
        builder.setView(view)
        tvBmi.text = bmi.toString()

        button_ok.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }


    private fun validateUnits(): Boolean {
        var isValid = true
        if (binding.edtWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.edtHeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }


    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCalculator)
        if ((activity as AppCompatActivity).supportActionBar != null) {
        }
        binding.toolbarCalculator.title = "BMI CALCULATOR"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
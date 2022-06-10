package workout.home.homeworkout.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentFinishBinding
import workout.home.homeworkout.room.HistoryApp
import workout.home.homeworkout.room.HistoryDao
import workout.home.homeworkout.room.HistoryEntity
import java.text.SimpleDateFormat
import java.util.*

class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)

        toolBarSetUp()

        binding.btnFinish.setOnClickListener {
            requireActivity().onBackPressed()
        }

        //Database
        val dao = (requireActivity().application as HistoryApp).db.historyDao()
        addDateToDatabase(dao)


        return binding.root
    }


    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.d("Date", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = sdf.format(dateTime)

        Log.d("Time", "" + date)

        lifecycleScope.launch(Dispatchers.IO) {
            historyDao.insert(HistoryEntity(date))
        }
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFinishActivity)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
        }
        binding.toolbarFinishActivity.title = "Workout Completed"
        binding.toolbarFinishActivity.setNavigationOnClickListener {

            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
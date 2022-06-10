package workout.home.homeworkout.ui.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import workout.home.homeworkout.BuildConfig
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentHistoryBinding
import workout.home.homeworkout.room.HistoryAdapter
import workout.home.homeworkout.room.HistoryApp
import workout.home.homeworkout.room.HistoryDao
import workout.home.homeworkout.room.HistoryDataBase


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)


        //Toolbar Setup
        toolBarSetUp()


        val dao = (requireActivity().application as HistoryApp).db.historyDao()
        getAllCompletedDates(dao)



        return binding.root
    }

    private fun deleteAllDates() {
        lifecycleScope.launch {
            HistoryDataBase.getInstance(requireContext()).clearTables()
        }
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { allCompletedDatesList ->
                if (allCompletedDatesList.isNotEmpty()) {
                    binding.clNoData.visibility = View.INVISIBLE
                    binding.tvExerciseCompleted.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE

                    binding.rvHistory.layoutManager = LinearLayoutManager(context)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding.rvHistory.adapter = historyAdapter

                } else {
                    binding.clNoData.visibility = View.VISIBLE
                    binding.tvExerciseCompleted.visibility = View.INVISIBLE
                    binding.rvHistory.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHistory)
        if ((activity as AppCompatActivity).supportActionBar != null) {
        }
        binding.toolbarHistory.title = "HISTORY"
        setHasOptionsMenu(true)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_delete -> {
                customDialogForDeleteDate()
            }
            R.id.mi_share -> {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    var shareMessage = "\nShare with you loved ones\n\n"
                    shareMessage = """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${workout.home.homeworkout.BuildConfig.APPLICATION_ID}""".trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {
                }
                true
            }
            R.id.mi_rate_us -> {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + requireActivity().packageName)
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                        )
                    )
                }
                true
            }
            R.id.mi_privacy -> {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_miHistory_to_privacyPolicyFragment)
                true
            }
            R.id.mi_feedback -> {
                val intent = Intent(Intent.ACTION_SEND)
                val recipients = arrayOf("rishabh1112131415@gmail.com")
                intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                intent.putExtra(Intent.EXTRA_SUBJECT, "")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.putExtra(Intent.EXTRA_CC, "mailcc@gmail.com")
                intent.type = "text/html"
                intent.setPackage("com.google.android.gm")
                startActivity(Intent.createChooser(intent, "Send mail"))
                true
            }
        }

        return true
    }

    private fun customDialogForDeleteDate() {
        val builder = android.app.AlertDialog.Builder(context, R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.custom_delete_dialog, null)
        val button_ok = view.findViewById<Button>(R.id.btn_ok)
        val button_cancel = view.findViewById<Button>(R.id.btn_cancel)
        builder.setView(view)

        button_ok.setOnClickListener {
            deleteAllDates()
            Toast.makeText(context, "History Deleted Successfully", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        button_cancel.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
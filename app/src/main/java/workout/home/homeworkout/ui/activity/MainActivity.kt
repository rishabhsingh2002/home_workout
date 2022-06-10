package workout.home.homeworkout.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_fragment)
        binding.bottomNavigation.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.absFragment || destination.id ==
                R.id.absExerciseFragment || destination.id == R.id.finishFragment
                || destination.id == R.id.splashFragment || destination.id == R.id.chestFragment
                || destination.id == R.id.chestExerciseFragment || destination.id == R.id.armsFragment
                || destination.id == R.id.armsExerciseFragment || destination.id == R.id.backFragment
                || destination.id == R.id.backExerciseFragment || destination.id == R.id.legFragment
                || destination.id == R.id.legExerciseFragment || destination.id == R.id.privacyPolicyFragment
            ) {
                binding.bottomNavigation.visibility = View.GONE
            } else {

                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

    }

}
package ua.mrrobot1413.movies.ui

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.databinding.ActivityMainBinding
import ua.mrrobot1413.movies.ui.favorite.FavoriteFragmentDirections
import ua.mrrobot1413.movies.utils.NotificationHelper.Companion.MOVIE_ID

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navController = findNavController(R.id.nav_host_fragment)
        setupSmoothBottomMenu(navController)

        if (intent.getIntExtra(MOVIE_ID, -1) != -1) {
            navController.navigate(R.id.fragmentFavorite)
            navController.navigate(
                FavoriteFragmentDirections.actionFragmentFavoriteToFragmentDetailedMovie()
                    .setId(intent.getIntExtra(MOVIE_ID, -1))
            )
        }
    }

    private fun setupSmoothBottomMenu(navController: NavController) {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_main)
        val menu = popupMenu.menu
        binding?.bottomBar?.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("New")
    }
}
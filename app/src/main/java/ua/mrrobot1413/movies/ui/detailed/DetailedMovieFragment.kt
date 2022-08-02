package ua.mrrobot1413.movies.ui.detailed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.databinding.FragmentDetailedMovieBinding

@AndroidEntryPoint
class DetailedMovieFragment: Fragment(R.layout.fragment_detailed_movie) {

    private val binding: FragmentDetailedMovieBinding by viewBinding()
    private val viewModel: DetailedMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {

        }
    }

    private fun initObservers() {
        viewModel.run {
            binding.run {

            }
        }
    }
}
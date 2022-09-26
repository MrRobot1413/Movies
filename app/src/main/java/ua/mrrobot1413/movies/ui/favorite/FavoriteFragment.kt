package ua.mrrobot1413.movies.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.Result
import ua.mrrobot1413.movies.data.storage.model.FavoriteMovie
import ua.mrrobot1413.movies.data.storage.model.Movie
import ua.mrrobot1413.movies.databinding.FragmentFavoriteBinding
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding: FragmentFavoriteBinding by viewBinding()
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy {
        FavoriteRecyclerViewAdapter {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

            viewModel.getFavoriteMovies()
        }
    }

    private fun initObservers() {
        viewModel.run {
            binding.run {
                movies.observe(viewLifecycleOwner) {
                    when (it.status) {
                        RequestStatus.LOADING -> loading()
                        RequestStatus.SUCCESS -> {
                            successLoad(it.data?.isEmpty() == true)
                            adapter.submitList(it.data)
                        }
                        RequestStatus.ERROR -> {
                            showSnackbar(
                                requireView(),
                                getString(R.string.unexpected_error_occurred),
                                Snackbar.LENGTH_SHORT
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loading() {
        binding.run {
            txtEmptyList.visibility = View.GONE
            recyclerView.visibility = View.GONE
            lottieLoaderAnimation.visibility = View.VISIBLE
        }
    }

    private fun successLoad(isEmptyList: Boolean) {
        binding.run {
            txtEmptyList.isVisible = isEmptyList
            recyclerView.visibility = View.VISIBLE
            lottieLoaderAnimation.visibility = View.GONE
        }
    }
}
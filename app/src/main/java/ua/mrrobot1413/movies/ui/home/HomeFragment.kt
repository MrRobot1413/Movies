package ua.mrrobot1413.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.FooterAdapter
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.databinding.FragmentHomeBinding
import ua.mrrobot1413.movies.ui.home.recycler.LatestRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.home.recycler.TopRatedRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.home.recycler.UpcomingRecyclerViewAdapter
import ua.mrrobot1413.movies.utils.UIUtils.hide
import ua.mrrobot1413.movies.utils.UIUtils.show
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    private val popularAdapter by lazy {
        LatestRecyclerViewAdapter()
    }
    private val topRatedAdapter by lazy {
        TopRatedRecyclerViewAdapter()
    }
    private val upcomingAdapter by lazy {
        UpcomingRecyclerViewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            viewModel.getMovies()
//
//            swipeRefreshLayout.setOnRefreshListener {
//                viewModel.getMovies()
//            }

            popularRecyclerView.adapter = popularAdapter.withLoadStateFooter(FooterAdapter())
            popularRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            topRatedRecyclerView.adapter = topRatedAdapter.withLoadStateFooter(FooterAdapter())
            topRatedRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            upcomingRecyclerView.adapter = upcomingAdapter.withLoadStateFooter(FooterAdapter())
            upcomingRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initObservers() {
        viewModel.run {
            binding.run {
                lifecycleScope.launch {
                    popularMovies.collect {
                        println("Popular: " + it?.status)
                        when (it?.status) {
                            RequestStatus.LOADING -> {
                                root.forEach { view ->
                                    if(view.id != lottieLoaderAnimation.id) {
                                        view.hide()
                                    }
                                }
                                lottieLoaderAnimation.show()
                            }
                            RequestStatus.SUCCESS -> {
                                it.data?.let { data -> popularAdapter.submitData(data) }
                            }
                            RequestStatus.ERROR -> {
                                showSnackbar(
                                    requireView(),
                                    getString(R.string.unexpected_error_occurred)
                                )
                            }
                            else -> {
                                println("err")
                            }
                        }
                    }
                }
                lifecycleScope.launch {
                    topRatedMovies.collect {
                        println("Top: " + it?.status)
                        when (it?.status) {
                            RequestStatus.SUCCESS -> {
                                topRatedRecyclerView.show()
                                it.data?.let { data -> topRatedAdapter.submitData(data) }
                            }
                            RequestStatus.ERROR -> {
                                showSnackbar(
                                    requireView(),
                                    getString(R.string.unexpected_error_occurred)
                                )
                            }
                            else -> {}
                        }
                    }
                }
                lifecycleScope.launch {
                    upcomingMovies.collect {
                        println("Upcoming: " + it?.status)
                        when (it?.status) {
                            RequestStatus.SUCCESS -> {
                                root.forEach { view ->
                                    if(view.id != lottieLoaderAnimation.id) {
                                        view.show()
                                    }
                                }
                                lottieLoaderAnimation.hide()
                                it.data?.let { data -> upcomingAdapter.submitData(data) }
                            }
                            RequestStatus.ERROR -> {
                                showSnackbar(
                                    requireView(),
                                    getString(R.string.unexpected_error_occurred)
                                )
                            }
                            null -> {
                                println(null)
                            }
                        }
                    }
                }
            }
        }
    }
}
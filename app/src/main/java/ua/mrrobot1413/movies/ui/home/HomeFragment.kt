package ua.mrrobot1413.movies.ui.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.App
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.FooterAdapter
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.RequestType
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

    override fun onDestroyView() {
        super.onDestroyView()

        binding.run {
            popularRecyclerView.visibility = View.GONE
            upcomingRecyclerView.visibility = View.GONE
            topRatedRecyclerView.visibility = View.GONE
        }
    }

    private fun init() {
        binding.run {
            viewModel.getMovies()

            searchEt.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToSearchFragment())
            }
            searchEtHolder.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToSearchFragment())
            }
            searchFieldHolder.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToSearchFragment())
            }

            txtViewAllPopular.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToViewAllFragment(
                        RequestType.POPULAR
                    )
                )
            }
            txtViewAllTopRated.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToViewAllFragment(
                        RequestType.TOP_RATED
                    )
                )
            }
            txtViewAllUpcoming.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToViewAllFragment(
                        RequestType.UPCOMING
                    )
                )
            }

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
                popularMovies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            loading()
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            lifecycleScope.launch {
                                it.data?.let { data -> popularAdapter.submitData(data) }
                            }
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
                topRatedMovies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            loading()
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            lifecycleScope.launch {
                                it.data?.let { data -> topRatedAdapter.submitData(data) }
                            }
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

                upcomingMovies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            loading()
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            lifecycleScope.launch {
                                it.data?.let { data ->
                                    upcomingAdapter.submitData(data)
                                }
                            }
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

    private fun loading() {
        binding.run {
            txtPopular.hide()
            txtViewAllPopular.hide()
            txtTopRated.hide()
            txtViewAllTopRated.hide()
            txtUpcoming.hide()
            txtViewAllUpcoming.hide()
            popularRecyclerView.hide()
            topRatedRecyclerView.hide()
            upcomingRecyclerView.hide()
            lottieLoaderAnimation.show()
        }
    }

    private fun successLoad() {
        binding.run {
            txtPopular.show()
            txtViewAllPopular.show()
            txtTopRated.show()
            txtViewAllTopRated.show()
            txtUpcoming.show()
            txtViewAllUpcoming.show()
            popularRecyclerView.show()
            topRatedRecyclerView.show()
            upcomingRecyclerView.show()
            lottieLoaderAnimation.hide()
        }
    }
}
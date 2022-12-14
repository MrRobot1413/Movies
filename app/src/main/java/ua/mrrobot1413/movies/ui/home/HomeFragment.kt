package ua.mrrobot1413.movies.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.data.network.model.MovieResponseModel
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.databinding.FragmentHomeBinding
import ua.mrrobot1413.movies.ui.home.recycler.LatestRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.home.recycler.TopRatedRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.home.recycler.UpcomingRecyclerViewAdapter
import ua.mrrobot1413.movies.utils.UIUtils.hide
import ua.mrrobot1413.movies.utils.UIUtils.show
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar

@ViewModelScoped
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val popularAdapter by lazy {
        LatestRecyclerViewAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentDetailedMovie().setId(it)
            )
        }
    }

    private val topRatedAdapter by lazy {
        TopRatedRecyclerViewAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentDetailedMovie().setId(it)
            )
        }
    }

    private val upcomingAdapter by lazy {
        UpcomingRecyclerViewAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToFragmentDetailedMovie().setId(it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            searchView.setOnClickListener {
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

            viewModel.loadNextPopularPage()
            viewModel.loadNextTopPage()
            viewModel.loadNextUpcomingPage()

            initLists()
        }
    }

    private fun initObservers() {
        var popularFirstLoad = true
        var topFirstLoad = true
        var upcomingFirstLoad = true
        viewModel.run {
            binding.run {
                popularMovies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            if (popularFirstLoad) {
                                loading()
                                popularAdapter.submitList(listOf())
                                popularFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.let { movies ->
                                (popularAdapter.currentList as MutableList<MovieResponseModel>).plus(
                                    movies
                                )
                            }?.toSet()?.toList()
                            popularAdapter.submitList(list)
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
                            if (topFirstLoad) {
                                loading()
                                topRatedAdapter.submitList(listOf())
                                topFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.let { movies ->
                                (topRatedAdapter.currentList as MutableList<MovieResponseModel>).plus(
                                    movies
                                )
                            }?.toSet()?.toList()
                            topRatedAdapter.submitList(list)
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
                upcomingMovies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            if (upcomingFirstLoad) {
                                loading()
                                upcomingAdapter.submitList(listOf())
                                upcomingFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.let { movies ->
                                (upcomingAdapter.currentList as MutableList<MovieResponseModel>).plus(
                                    movies
                                )
                            }?.toSet()?.toList()
                            upcomingAdapter.submitList(list)
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

    private fun initLists() {
        binding.run {
            popularRecyclerView.adapter = popularAdapter
            popularRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            popularRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = popularRecyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 1.1) {
                        viewModel.loadNextPopularPage()
                    }
                }
            })

            topRatedRecyclerView.adapter = topRatedAdapter
            topRatedRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            topRatedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = topRatedRecyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 1.1) {
                        viewModel.loadNextTopPage()
                    }
                }
            })

            upcomingRecyclerView.adapter = upcomingAdapter
            upcomingRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            upcomingRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = upcomingRecyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 1.1) {
                        viewModel.loadNextUpcomingPage()
                    }
                }
            })
        }
    }
}
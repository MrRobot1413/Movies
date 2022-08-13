package ua.mrrobot1413.movies.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.FooterAdapter
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.databinding.FragmentHomeBinding
import ua.mrrobot1413.movies.ui.MainActivity
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

            viewModel.getPopularMovies(1)
            viewModel.getTopRatedMovies(1)
            viewModel.getUpcomingMovies(1)

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
                            if(popularFirstLoad) {
                                loading()
                                popularFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.results?.let { movies ->
                                (popularAdapter.currentList as MutableList<Movie>).plus(
                                    movies
                                )
                            }
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
                            if(topFirstLoad) {
                                loading()
                                topFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.results?.let { movies ->
                                (topRatedAdapter.currentList as MutableList<Movie>).plus(
                                    movies
                                )
                            }
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
                            if(upcomingFirstLoad) {
                                loading()
                                upcomingFirstLoad = false
                            }
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()
                            val list = it.data?.results?.let { movies ->
                                (upcomingAdapter.currentList as MutableList<Movie>).plus(
                                    movies
                                )
                            }
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

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                        viewModel.popularPages++
                        viewModel.getPopularMovies(viewModel.popularPages)
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

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                        viewModel.topPages++
                        viewModel.getTopRatedMovies(viewModel.topPages)
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

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                        viewModel.upcomingPages++
                        viewModel.getUpcomingMovies(viewModel.upcomingPages)
                    }
                }
            })
        }
    }
}
package ua.mrrobot1413.movies.ui.viewAll

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.ExtendedFooterAdapter
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.databinding.FragmentViewAllBinding
import ua.mrrobot1413.movies.ui.MainActivity
import ua.mrrobot1413.movies.ui.home.HomeFragmentDirections
import ua.mrrobot1413.movies.utils.Constants.REQUEST_TYPE
import ua.mrrobot1413.movies.utils.UIUtils.hide
import ua.mrrobot1413.movies.utils.UIUtils.show
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar

@AndroidEntryPoint
class ViewAllFragment : Fragment(R.layout.fragment_view_all) {

    private val binding: FragmentViewAllBinding by viewBinding()
    private val viewModel: ViewAllViewModel by viewModels()
    private val adapter by lazy {
        ViewAllRecyclerViewAdapter {
            findNavController().navigate(ViewAllFragmentDirections.actionViewAllFragmentToFragmentDetailedMovie().setId(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            val requestType = arguments?.getParcelable<RequestType>(REQUEST_TYPE)
            requestType?.let { viewModel.getMovies(requestType = it, 1) }

            topAppBar.title = when (requestType) {
                RequestType.POPULAR -> getString(R.string.popular)
                RequestType.TOP_RATED -> getString(R.string.top_rated)
                RequestType.UPCOMING -> getString(R.string.upcoming)
                else -> ""
            }
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                        viewModel.pages++
                        if (requestType != null) {
                            viewModel.getMovies(requestType, viewModel.pages)
                        }
                    }
                }
            })
        }
    }

    private fun initObservers() {
        viewModel.run {
            binding.run {
                movies.observe(viewLifecycleOwner) {
                    when (it?.status) {
                        RequestStatus.LOADING -> {
                            lottieLoaderAnimation.show()
                        }
                        RequestStatus.SUCCESS -> {
                            lottieLoaderAnimation.hide()
                            val list = it.data?.results?.let { movies ->
                                (adapter.currentList as MutableList<Movie>).plus(
                                    movies
                                )
                            }
                            adapter.submitList(list)
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
        }
    }
}
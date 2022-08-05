package ua.mrrobot1413.movies.ui.viewAll

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.ExtendedFooterAdapter
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
            requestType?.let { viewModel.getMovies(requestType = it) }

            topAppBar.title = when (requestType) {
                RequestType.POPULAR -> getString(R.string.popular)
                RequestType.TOP_RATED -> getString(R.string.top_rated)
                RequestType.UPCOMING -> getString(R.string.upcoming)
                else -> ""
            }
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            recyclerView.adapter = adapter.withLoadStateFooter(ExtendedFooterAdapter())
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
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
                            lifecycleScope.launch {
                                it.data?.let { data -> adapter.submitData(data) }
                            }
                        }
                        RequestStatus.ERROR -> {
                            showSnackbar(
                                requireView(),
                                getString(R.string.unexpected_error_occurred)
                            )
                            while (true) {
                                lifecycleScope.launch {
                                    delay(4000)
                                    arguments?.getParcelable<RequestType>(REQUEST_TYPE)
                                        ?.let { type -> getMovies(type) }
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
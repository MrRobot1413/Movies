package ua.mrrobot1413.movies.ui.detailed

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.databinding.FragmentDetailedMovieBinding
import ua.mrrobot1413.movies.ui.detailed.recycler.GenresRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.detailed.recycler.SimilarMoviesRecyclerViewAdapter
import ua.mrrobot1413.movies.utils.Constants.ID
import ua.mrrobot1413.movies.utils.Constants.IMG_URL
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar
import ua.mrrobot1413.movies.utils.UIUtils.toggleReadMoreTextView
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailedMovieFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailedMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailedMovieViewModel by viewModels()

    private val genresAdapter by lazy {
        GenresRecyclerViewAdapter()
    }
    private val similarMoviesAdapter by lazy {
        SimilarMoviesRecyclerViewAdapter {
            findNavController().navigate(DetailedMovieFragmentDirections.actionFragmentDetailedMovieSelf().setId(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            makeTransparentBackground()
            val id = arguments?.getInt(ID)
            id?.let {
                viewModel.getMovieDetails(it)
                viewModel.getSimilarMovies(it)
            }

            imgClose.setOnClickListener { dismiss() }

            txtOverview.toggleReadMoreTextView(root, 4)
            txtOverview.setOnClickListener {
                txtOverview.toggleReadMoreTextView(root, 4)
            }

            genresRecyclerView.adapter = genresAdapter
            genresRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            similarMoviesRecyclerView.adapter = similarMoviesAdapter
            similarMoviesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        viewModel.run {
            binding.run {
                viewModel.details.observe(viewLifecycleOwner) {
                    when (it.status) {
                        RequestStatus.LOADING -> {
                            loading()
                        }
                        RequestStatus.SUCCESS -> {
                            successLoad()

                            Glide.with(requireView())
                                .load(IMG_URL + it.data?.backgroundPoster)
                                .apply(
                                    RequestOptions.centerCropTransform()
                                        .transform(
                                            CenterCrop(),
                                            GranularRoundedCorners(20f, 20f, 0f, 0f)
                                        )
                                )
                                .into(movieImg)

                            it.data?.let { data ->
                                txtTitle.text = data.title
                                genresAdapter.submitList(data.genres)
                                txtOverview.text = data.overview
                                txtYear.text = data.releaseDate.split("-")[0]
                                txtRating.text = data.rating.toString()
                                txtReleaseDate.text = data.releaseDate.replace("-", "/")
                                txtOriginalTitle.text = data.originalTitle
                                txtOriginalLanguage.text = data.originalLanguage
                                txtDuration.text =
                                    data.runtime.toString() + " " + getString(R.string.min)
                                val format = NumberFormat.getCurrencyInstance()
                                format.maximumFractionDigits = 0
                                format.currency = Currency.getInstance("USD")

                                val budget = format.format(data.budget)
                                txtBudget.text = budget
                                txtStatus.text = data.status
                            }
                        }
                        RequestStatus.ERROR -> {
                            showSnackbar(
                                requireView(),
                                getString(R.string.unexpected_error_occurred)
                            )
                        }
                    }
                }
                similarMovies.observe(viewLifecycleOwner) {
                    lifecycleScope.launch {
                        similarMoviesAdapter.submitList(it?.results)
                    }
                }
            }
        }
    }

    private fun successLoad() {
        binding.run {
            lottieLoaderAnimation.isVisible = false
            imgAddToFavorite.isVisible = true
            detailedMovieInfoLayout.isVisible = true
            headerLayout.isVisible = true
        }
    }

    private fun loading() {
        binding.run {
            lottieLoaderAnimation.isVisible = false
            imgAddToFavorite.isVisible = false
            detailedMovieInfoLayout.isVisible = false
            headerLayout.isVisible = true
        }
    }

    private fun makeTransparentBackground() {
        val bottomSheet = view?.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
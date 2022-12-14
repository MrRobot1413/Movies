package ua.mrrobot1413.movies.ui.detailed

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.data.network.model.DetailedMovie
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.data.network.model.RequestType
import ua.mrrobot1413.movies.databinding.FragmentDetailedMovieBinding
import ua.mrrobot1413.movies.ui.detailed.recycler.GenresRecyclerViewAdapter
import ua.mrrobot1413.movies.ui.detailed.recycler.SimilarMoviesRecyclerViewAdapter
import ua.mrrobot1413.movies.utils.Constants.ID
import ua.mrrobot1413.movies.utils.Constants.IMG_URL
import ua.mrrobot1413.movies.utils.ReminderWorker
import ua.mrrobot1413.movies.utils.UIUtils.show
import ua.mrrobot1413.movies.utils.UIUtils.showSnackbar
import ua.mrrobot1413.movies.utils.UIUtils.toggleReadMoreTextView
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
            findNavController().navigate(
                DetailedMovieFragmentDirections.actionFragmentDetailedMovieSelf().setId(it)
            )
        }
    }
    private var detailedMovie: DetailedMovie? = null

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
                lifecycleScope.launch {
                    var isToRemind = viewModel.isReminderMovie(id)
                    if (isToRemind) {
                        binding.imgRemind.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_timer_off
                            )
                        )
                    } else {
                        binding.imgRemind.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_timer
                            )
                        )
                    }
                    imgRemind.setOnClickListener {
                        if (isToRemind) {
                            // TODO
                            // delete reminder
                            detailedMovie?.id?.let { id -> viewModel.deleteReminder(id) }
                            isToRemind = false
                            binding.imgRemind.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_timer
                                )
                            )
                        } else {
                            val calendar = Calendar.getInstance()
                            val datePicker = DatePickerDialog(
                                requireContext(),
                                R.style.ReminderDialogTheme,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(Calendar.YEAR, year)
                                    calendar.set(Calendar.MONTH, month)
                                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                    val timePicker = TimePickerDialog(
                                        requireContext(),
                                        R.style.ReminderDialogTheme,
                                        { _, hour, minute ->
                                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                                            calendar.set(Calendar.MINUTE, minute)
                                            // Set reminder
                                            lifecycleScope.launch {
                                                detailedMovie?.id?.let { id ->
                                                    createReminder(
                                                        getString(R.string.reminder),
                                                        "${getString(R.string.reminder_to_watch_description)} '${detailedMovie?.title}'",
                                                        calendar.timeInMillis,
                                                        id
                                                    )
                                                    isToRemind = true
                                                    binding.imgRemind.setImageDrawable(
                                                        ContextCompat.getDrawable(
                                                            requireContext(),
                                                            R.drawable.ic_timer_off
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        true
                                    )
                                    timePicker.show()
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        }
                    }

                    var isFavorite = viewModel.isFavoriteMovie(id)
                    if (!isFavorite) {
                        binding.imgAddToFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_star_filled_yellow
                            )
                        )
                    } else {
                        binding.imgAddToFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_star_unfilled
                            )
                        )
                    }
                    imgAddToFavorite.setOnClickListener {
                        changeFavoriteButtonStatus(isFavorite)
                        isFavorite = !isFavorite
                    }
                }
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
                                detailedMovie = data
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

    private suspend fun createReminder(
        title: String,
        message: String,
        timeDelay: Long,
        movieId: Int
    ) {
        val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelay - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "title" to title,
                    "message" to message,
                    "movieId" to movieId,
//                    "isToRemind" to detailedMovie?.id?.let { viewModel.isReminderMovie(it) }
                )
            )
            .build()

        detailedMovie?.let { viewModel.createReminder(it.id, it) }
//        reminderRequest.id
        WorkManager.getInstance(requireContext()).enqueue(reminderRequest)
    }

    private fun successLoad() {
        binding.run {
            lottieLoaderAnimation.isVisible = false
            imgAddToFavorite.isVisible = true
            detailedMovieInfoLayout.isVisible = true
            headerLayout.isVisible = true
            imgAddToFavorite.isVisible = true
            imgRemind.isVisible = true
        }
    }

    private fun loading() {
        binding.run {
            lottieLoaderAnimation.isVisible = false
            imgAddToFavorite.isVisible = false
            detailedMovieInfoLayout.isVisible = false
            headerLayout.isVisible = true
            imgAddToFavorite.isVisible = false
            imgRemind.isVisible = false
        }
    }

    private fun changeFavoriteButtonStatus(isFavorite: Boolean) {
        if (isFavorite) {
            binding.imgAddToFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_star_filled_yellow
                )
            )
            detailedMovie?.let { movie ->
                arguments?.getInt(ID)?.let { id -> viewModel.addToFavorite(id, movie) }
            }
        } else {
            binding.imgAddToFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_star_unfilled
                )
            )
            detailedMovie?.let { movie ->
                arguments?.getInt(ID)?.let { id -> viewModel.removeFromFavorite(id, movie.id) }
            }
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
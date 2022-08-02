package ua.mrrobot1413.movies.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.App
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.base.ExtendedFooterAdapter
import ua.mrrobot1413.movies.data.network.model.RequestStatus
import ua.mrrobot1413.movies.databinding.FragmentSearchBinding
import ua.mrrobot1413.movies.utils.UIUtils
import ua.mrrobot1413.movies.utils.UIUtils.hide
import ua.mrrobot1413.movies.utils.UIUtils.show

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy {
        SearchRecyclerViewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initObservers()
    }

    private fun init() {
        binding.run {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            searchEt.requestFocus()
            UIUtils.showSoftKeyboard(activity as AppCompatActivity, searchEt)
            searchEt.setOnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    UIUtils.hideKeyboard(activity as AppCompatActivity, searchEt)
                }
                true
            }
            searchEt.doOnTextChanged { text, _, _, _ ->
                if (text?.trim()?.isNotEmpty() == true) {
                    lifecycleScope.launch {
                        delay(600)
                        viewModel.search(text.trim().toString())
                    }
                }
            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
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
                                it.data?.let { data ->
                                    adapter.submitData(
                                        data
                                    )
                                }
                            }
                        }
                        RequestStatus.ERROR -> {
                            UIUtils.showSnackbar(
                                requireView(),
                                getString(R.string.unexpected_error_occurred)
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
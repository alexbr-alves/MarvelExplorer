package alexProject.fragment

import alexProject.activity.MoviesActivity
import alexProject.adapters.TimelineAdapter
import alexProject.databinding.FragmentTimelineBinding
import alexProject.state.ResourceState
import alexProject.util.Converts
import alexProject.util.observe
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.TimelineViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TimelineFragment :
    BaseFragment<FragmentTimelineBinding, TimelineViewModel>() {
    override val viewModel : TimelineViewModel by viewModels()
    private val timelineAdapter by lazy { TimelineAdapter() }
    private var converts = Converts()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserve()
        clickAdapter()
    }

    private fun clickAdapter() {
        timelineAdapter.setOnClickListener { model ->
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToDetailsComicFragment(
                    model
                )
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            fetch()
            observe(isLoading) { isLoading ->
                binding.progressbarSearch.setVisible(isLoading)
            }
            observe(onError) { onError ->
                Timber.e(onError)
            }
            observe(listItems) { result ->
                result.data?.data?.result?.let {
                    timelineAdapter.timelineList = it.toList()
                }
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = timelineAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentTimelineBinding =
        FragmentTimelineBinding.inflate(inflater, container, false)
}
package alexProject.fragment

import alexProject.activity.MainActivity
import alexProject.adapters.StoryAdapter
import alexProject.adapters.TimelineAdapter
import alexProject.databinding.FragmentTimelineBinding
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.Converts
import alexProject.util.observe
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

@AndroidEntryPoint
class TimelineFragment :
    BaseFragment<FragmentTimelineBinding, TimelineViewModel>() {
    override val viewModel: TimelineViewModel by viewModels()
    private val timelineAdapter by lazy { TimelineAdapter() }
    private val storyAdapter by lazy { StoryAdapter() }
    private val converts = Converts()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserve()
        clickAdapter()
    }

    private fun clickAdapter() {
        timelineAdapter.setOnClickListener { comicModel ->
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToDetailsFragment(
                    comic = comicModel,
                    character = Constants.CHARACTER_INVALID,
                    event = Constants.EVENT_INVALID
                )
            findNavController().navigate(action)
        }

        storyAdapter.setOnClickListener { favoriteModel ->
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToDetailsFragment(
                    comic = Constants.COMIC_INVALID,
                    character = converts.favoriteToCharacter(favoriteModel),
                    event = Constants.EVENT_INVALID
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
                (activity as MainActivity).showError(onError)
            }
            observe(listItems) { result ->
                binding.recycleStorys.setVisible()
                timelineAdapter.timelineList = result
            }
            storyList.collect { resource ->
                when(resource) {
                    is ResourceState.Success -> {
                        resource.data?.let {
                            storyAdapter.list = it.toList()
                        }
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = timelineAdapter
            layoutManager = LinearLayoutManager(context)
        }
        recycleStorys.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimelineBinding =
        FragmentTimelineBinding.inflate(inflater, container, false)
}
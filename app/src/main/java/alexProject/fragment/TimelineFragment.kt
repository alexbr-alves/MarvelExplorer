package alexProject.fragment

import alexProject.activity.MainActivity
import alexProject.adapters.StoryAdapter
import alexProject.adapters.TimelineAdapter
import alexProject.data.model.StoryModelData
import alexProject.databinding.FragmentTimelineBinding
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

    private fun onClickStoryAdapter(list: StoryModelData) {
        storyAdapter.setOnClickListener {
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToStoryFragment(
                    id = it.id,
                    storyModel = list
                )
            findNavController().navigate(action)
        }
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

    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            getStory()
            getComics()
            observe(onLoading) {
                binding.progressbarSearch.setVisible()
            }
            observe(onError) { onError ->
                (activity as MainActivity).showError(onError.message)
            }
            observe(listComics) { result ->
                result?.let {
                    timelineAdapter.timelineList = it
                    binding.recycleStorys.setVisible()
                }
            }
            observe(listStoryComics) { storyList ->
                onClickStoryAdapter(storyList)
            }
            observe(storyList) {
                storyAdapter.list = it.toList()
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

    override fun onResume() {
        super.onResume()
        if (storyAdapter.itemCount != 0) {
            binding.recycleStorys.setVisible()
        }
    }
}
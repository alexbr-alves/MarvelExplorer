package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.adapters.TimelineAdapter
import alex.lop.io.alexProject.data.model.timeline.TimelineType
import alex.lop.io.alexProject.databinding.FragmentTimelineBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.Converts
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.viewModel.TimelineViewModel
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
        timelineAdapter?.setOnClickListener { model ->
            when (model.type) {
                TimelineType.CHARACTER -> {
                    val action =
                        TimelineFragmentDirections.actionSearchCharacterFragmentToDetailsCharacterFragment(
                            converts.timelineToCharacter(model)
                        )
                    findNavController().navigate(action)
                }

                TimelineType.COMIC -> {
                    val action =
                        TimelineFragmentDirections.actionTimelineFragmentToDetailsComicFragment(
                            converts.timelineToComic(model)
                        )
                    findNavController().navigate(action)
                }

                TimelineType.EVENT -> {
                    val action =
                        TimelineFragmentDirections.actionTimelineFragmentToDetailsEventFragment(
                            converts.timelineToEvent(model)
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.listItems.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.setInvisible()
                    result.data?.let { item ->
                        if (item.isNotEmpty()) {
                            timelineAdapter?.timelineList = item.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressbarSearch.setInvisible()
                    result.message?.let { message ->
                        Timber.tag("TimelineFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressbarSearch.setVisible()
                }

                else -> {}
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
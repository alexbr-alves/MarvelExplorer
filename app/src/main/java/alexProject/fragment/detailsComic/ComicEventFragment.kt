package alexProject.fragment.detailsComic

import alexProject.adapters.EventDetailsAdapter
import alexProject.databinding.FragmentEventComicBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailsComics.ComicEventsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ComicEventFragment(private val comicId : Int) :
    BaseFragment<FragmentEventComicBinding, ComicEventsViewModel>() {
    override val viewModel : ComicEventsViewModel by viewModels()
    private val eventDetailsAdapter by lazy { EventDetailsAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventComicBinding = FragmentEventComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(comicId)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        eventDetailsAdapter.setOnClickListener { eventModel ->
            val action = ComicDetailsFragmentDirections.actionDetailsComicFragmentToDetailsEventFragment(eventModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvEventCharacter.apply {
            adapter = eventDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            eventDetailsAdapter.events = values.data.result.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        Timber.tag("EventComicFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBarDetail.setVisible()
                }

                else -> {
                }
            }
        }
    }

}
package alexProject.fragment.detailsComic

import alexProject.adapters.EventDetailsAdapter
import alexProject.databinding.FragmentEventComicBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.setGone
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ComicEventFragment(private val comicId : Int) :
    BaseFragment<FragmentEventComicBinding, ComicEventsViewModel>() {
    override val viewModel : ComicEventsViewModel by viewModels()
    private val eventDetailsAdapter by lazy { EventDetailsAdapter() }
    private var totalItems : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventComicBinding = FragmentEventComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        eventDetailsAdapter.setOnClickListener { eventModel ->
            val action =
                ComicDetailsFragmentDirections.actionDetailsComicFragmentToDetailsEventFragment(
                    eventModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                ComicDetailsFragmentDirections.actionDetailsComicFragmentToEventFragment(
                    Constants.COMIC,
                    comicId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvEventCharacter.apply {
            adapter = eventDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItems > 10) {
                        textSeeMore.setVisible()
                    } else {
                        textSeeMore.setGone()
                    }
                }
            })
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.fetch(comicId)
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            eventDetailsAdapter.events = values.data.result.toList()
                            totalItems = values.data.total
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
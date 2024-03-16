package alexProject.fragment.comic

import alexProject.adapters.event.EventDetailsAdapter
import alexProject.databinding.FragmentEventComicBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.comics.ComicEventsViewModel
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
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ComicEventFragment(private val comicId: Int) :
    BaseFragment<FragmentEventComicBinding, ComicEventsViewModel>() {
    override val viewModel: ComicEventsViewModel by viewModels()
    private val eventDetailsAdapter by lazy { EventDetailsAdapter() }
    private var totalItems: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEventComicBinding = FragmentEventComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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
        with(viewModel) {
            fetch(comicId)
            observe(onError) { onError ->
                Timber.d(onError)
            }
            observe(isLoading) { isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(listItems) { result ->
                result.data?.data?.let { eventModelData ->
                    if (eventModelData.result.isEmpty()) {
                        binding.textEmpty.setVisible()
                    } else {
                        eventDetailsAdapter.events = eventModelData.result.toList()
                        totalItems = eventModelData.total
                    }
                }
            }
        }

    }

}
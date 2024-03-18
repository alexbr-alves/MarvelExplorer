package alexProject.fragment.event

import alexProject.adapters.event.EventDetailsAdapter
import alexProject.databinding.FragmentEventsCharapterBinding
import alexProject.fragment.BaseFragment
import alexProject.fragment.DetailsFragmentDirections
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.event.EventsViewPagerViewModel
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
class EventsViewPagerFragment(private val characterId: Int, private val origin: String) :
    BaseFragment<FragmentEventsCharapterBinding, EventsViewPagerViewModel>() {
    private val eventDetailsAdapter by lazy { EventDetailsAdapter() }
    override val viewModel : EventsViewPagerViewModel by viewModels()
    private var totalItems : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventsCharapterBinding =
        FragmentEventsCharapterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        eventDetailsAdapter.setOnClickListener { eventModel ->
            collectObserver()
            val action =
                DetailsFragmentDirections.actionDetailsFragmentSelf(
                    comic = Constants.COMIC_INVALID,
                    character = Constants.CHARACTER_INVALID,
                    event = eventModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            collectObserver()
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToEventFragment(
                    Constants.EVENT,
                    characterId
                )
            findNavController().navigate(action)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        with(viewModel) {
            fetchEvent(characterId, origin)
            observe(onError) {onError ->
                Timber.e(onError)
            }
            observe(isLoading) {isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(eventList) {result ->
                result.data?.data?.let { eventModelData ->
                    if (eventModelData.result.isNotEmpty()) {
                        eventDetailsAdapter.events = result.data.data.result.toList()
                        totalItems = result.data.data.total
                    } else {
                        binding.textEmpty.setVisible()
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvEvents.apply {
            adapter = eventDetailsAdapter
            layoutManager = GridLayoutManager(context, 2)
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

}

package alexProject.fragment.detailsCharacter

import alexProject.adapters.EventDetailsAdapter
import alexProject.databinding.FragmentEventsCharapterBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailCharacter.CharacterEventsViewModel
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
class CharacterEventsFragment(private val characterId : Int) :
    BaseFragment<FragmentEventsCharapterBinding, CharacterEventsViewModel>() {

    private val eventDetailsAdapter by lazy { EventDetailsAdapter() }
    override val viewModel : CharacterEventsViewModel by viewModels()

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventsCharapterBinding =
        FragmentEventsCharapterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchEvent(characterId)
        setupRecycleView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        eventDetailsAdapter.setOnClickListener { eventModel ->
            val action =
                CharacterDetailsFragmentDirections.actionDetailsCharacterFragmentToDetailsEventFragment(
                    eventModel
                )
            findNavController().navigate(action)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.eventList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            eventDetailsAdapter.events = values.data.result.toList()
                        } else {
                            binding.textEmpty.setVisible()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBarDetail.setVisible()
                }

                else -> {}
            }

        }
    }

    private fun setupRecycleView() = with(binding) {
        rvEvents.apply {
            adapter = eventDetailsAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }


}

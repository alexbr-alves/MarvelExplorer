package alexProject.fragment.detailsEvent


import alexProject.adapters.CharacterDetailsAdapter
import alexProject.databinding.FragmentCharacterEventBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailEvent.EventCharactersViewModel
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
class EventCharactersFragment(private val comicId : Int) :
    BaseFragment<FragmentCharacterEventBinding, EventCharactersViewModel>() {

    override val viewModel : EventCharactersViewModel by viewModels()
    private val characterDetailsAdapter by lazy { CharacterDetailsAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentCharacterEventBinding =
        FragmentCharacterEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(comicId)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        characterDetailsAdapter.setOnClickListener { characterModel ->
            val action = EventDetailsFragmentDirections.actionDetailsEventFragmentToDetailsCharacterFragment(characterModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = characterDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.results.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            characterDetailsAdapter.characters = values.data.results.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        Timber.tag("CharactersComicFragment").e("Error -> $message")
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

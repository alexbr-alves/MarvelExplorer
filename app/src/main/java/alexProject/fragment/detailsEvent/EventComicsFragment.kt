package alexProject.fragment.detailsEvent


import alexProject.adapters.ComicDetailsAdapter
import alexProject.databinding.FragmentComicEventBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailEvent.EventComicsViewModel
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
class EventComicsFragment(private val comicId : Int) :
    BaseFragment<FragmentComicEventBinding, EventComicsViewModel>() {

    override val viewModel : EventComicsViewModel by viewModels()
    private val comicDetailsAdapter by lazy { ComicDetailsAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentComicEventBinding =
        FragmentComicEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(comicId)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        comicDetailsAdapter.setOnClickListener { comicModel ->
            val action = EventDetailsFragmentDirections.actionDetailsEventFragmentToDetailsComicFragment(comicModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = comicDetailsAdapter
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
                            comicDetailsAdapter.comics = values.data.result.toList()
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

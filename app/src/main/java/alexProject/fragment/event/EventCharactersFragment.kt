package alexProject.fragment.event

import alexProject.adapters.character.CharacterDetailsAdapter
import alexProject.data.model.character.CharacterModelResponse
import alexProject.databinding.FragmentCharacterEventBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.event.EventCharactersViewModel
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
class EventCharactersFragment(private val comicId: Int) :
    BaseFragment<FragmentCharacterEventBinding, EventCharactersViewModel>() {

    override val viewModel: EventCharactersViewModel by viewModels()
    private val characterDetailsAdapter by lazy { CharacterDetailsAdapter() }
    private var totalItems: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterEventBinding =
        FragmentCharacterEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        characterDetailsAdapter.setOnClickListener { characterModel ->
            val action =
                EventDetailsFragmentDirections.actionDetailsEventFragmentToDetailsCharacterFragment(
                    characterModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                EventDetailsFragmentDirections.actionDetailsEventFragmentToCharacterFragment(
                    Constants.EVENT,
                    comicId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = characterDetailsAdapter
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
                Timber.e(onError)
            }
            observe(isLoading) { isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(listItems) { result ->
                handleList(result)

            }
        }
    }

    private fun handleList(result: ResourceState<CharacterModelResponse>) {
        result.data?.data?.let { characterModelData ->
            if (characterModelData.results.isEmpty()) {
                binding.textEmpty.setVisible()
            } else {
                characterDetailsAdapter.characters = characterModelData.results.toList()
                totalItems = characterModelData.total
            }
        }
    }
}

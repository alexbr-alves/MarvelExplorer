package alexProject.fragment.detailsComic


import alexProject.adapters.CharacterDetailsAdapter
import alexProject.databinding.FragmentCharacterComicBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.setGone
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailsComics.ComicCharactersViewModel
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
class ComicCharactersFragment(private val comicId : Int) :
    BaseFragment<FragmentCharacterComicBinding, ComicCharactersViewModel>() {

    override val viewModel : ComicCharactersViewModel by viewModels()
    private val characterDetailsAdapter by lazy { CharacterDetailsAdapter() }
    private var totalItems : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentCharacterComicBinding =
        FragmentCharacterComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacterComic(comicId)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        characterDetailsAdapter.setOnClickListener { characterModel ->
            val action =
                ComicDetailsFragmentDirections.actionDetailsComicFragmentToDetailsCharacterFragment(
                    characterModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                ComicDetailsFragmentDirections.actionDetailsComicFragmentToCharacterFragment(
                    Constants.COMIC,
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
        viewModel.comicCharacterList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.results.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            characterDetailsAdapter.characters = values.data.results.toList()
                            totalItems = values.data.total
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

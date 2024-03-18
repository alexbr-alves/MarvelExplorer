package alexProject.fragment.comic


import alexProject.adapters.character.CharacterDetailsAdapter
import alexProject.databinding.FragmentCharacterComicBinding
import alexProject.fragment.BaseFragment
import alexProject.fragment.character.DetailsFragmentDirections
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.comics.ComicCharactersViewModel
import android.os.Bundle
import android.util.Log
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
class CharactersViewPagerFragment(private val comicId : Int) :
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
            val action = DetailsFragmentDirections.actionDetailsFragmentSelf(
                comic = Constants.COMIC_INVALID,
                character = characterModel,
                event = Constants.EVENT_INVALID,
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
            DetailsFragmentDirections.actionDetailsFragmentToCharacterFragment(
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
        with(viewModel) {
            observe(onError) {onError ->
                Timber.d(onError)
            }
            observe(isLoading) { isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(comicCharacterList) {result ->
                result.data?.data?.let { characterModels ->
                    if (characterModels.results.isEmpty()) {
                        binding.textEmpty.setVisible()
                    } else {
                        characterDetailsAdapter.characters = characterModels.results.toList()
                        totalItems = characterModels.total
                    }
                }
            }
        }
    }
}

package alexProject.fragment.character

import alexProject.adapters.comic.ComicDetailsAdapter
import alexProject.databinding.FragmentComicsCharapterBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.character.CharacterComicsViewModel
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
class CharacterComicsFragment(private val characterId : Int) :
    BaseFragment<FragmentComicsCharapterBinding, CharacterComicsViewModel>() {

    override val viewModel : CharacterComicsViewModel by viewModels()
    private val comicDetailsAdapter by lazy { ComicDetailsAdapter() }
    private var totalItems : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentComicsCharapterBinding =
        FragmentComicsCharapterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        comicDetailsAdapter.setOnClickListener { comicModel ->
            val action =
                CharacterDetailsFragmentDirections.actionDetailsCharacterFragmentToDetailsComicFragment(
                    comicModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                CharacterDetailsFragmentDirections.actionDetailsCharacterFragmentToComicsFragment(
                    Constants.CHARACTER,
                    characterId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvComics.apply {
            adapter = comicDetailsAdapter
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

    private fun collectObserver() = lifecycleScope.launch {
        with(viewModel) {
            fetchComic(characterId)
            observe(comicList) { result ->
                result.data?.data?.let { comicModelData ->
                    if (comicModelData.result.isNotEmpty()) {
                        comicDetailsAdapter.comics = comicModelData.result.toList()
                        totalItems = comicModelData.total
                    } else {
                        binding.textEmpty.setVisible()
                    }
                }
            }
            observe(onError) {
                Timber.e(it)
            }

            observe(isLoading) {isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
        }
    }
}
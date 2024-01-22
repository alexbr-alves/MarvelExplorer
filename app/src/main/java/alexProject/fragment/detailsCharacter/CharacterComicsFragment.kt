package alexProject.fragment.detailsCharacter

import alexProject.adapters.ComicDetailsAdapter
import alexProject.databinding.FragmentComicsCharapterBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.setGone
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.detailCharacter.CharacterComicsViewModel
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
        viewModel.fetchComic(characterId)
        viewModel.comicList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            comicDetailsAdapter.comics = values.data.result.toList()
                            totalItems = values.data.total
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
}
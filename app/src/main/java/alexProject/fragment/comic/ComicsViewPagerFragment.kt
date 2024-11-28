package alexProject.fragment.comic

import alexProject.activity.MainActivity
import alexProject.adapters.comic.ComicDetailsAdapter
import alexProject.databinding.FragmentComicsViewPagerBinding
import alexProject.fragment.BaseFragment
import alexProject.fragment.DetailsFragmentDirections
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.comics.ComicsViewPagerViewModel
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

@AndroidEntryPoint
class ComicsViewPagerFragment(private val characterId: Int,private val origin: String) :
    BaseFragment<FragmentComicsViewPagerBinding, ComicsViewPagerViewModel>() {

    override val viewModel : ComicsViewPagerViewModel by viewModels()
    private val comicDetailsAdapter by lazy { ComicDetailsAdapter() }
    private var totalItems : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentComicsViewPagerBinding =
        FragmentComicsViewPagerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        comicDetailsAdapter.setOnClickListener { comicModel ->
            val action =
                DetailsFragmentDirections.actionDetailsFragmentSelf(
                    comic = comicModel,
                    character = Constants.CHARACTER_INVALID,
                    event = Constants.EVENT_INVALID

                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToComicsFragment(
                    Constants.CHARACTER,
                    characterId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvComics.apply {
            adapter = comicDetailsAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= INITIAL_POSITION && totalItems > MAX_SIZE) {
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
            fetchComic(characterId, origin)
            observe(comicList) { result ->
                result.data?.apiResponse?.let { comicModelData ->
                    if (comicModelData.entityList.isNotEmpty()) {
                        comicDetailsAdapter.comics = comicModelData.entityList.toList()
                        totalItems = comicModelData.total
                    } else {
                        binding.textEmpty.setVisible()
                    }
                }
            }
            observe(onError) { onError ->
                (activity as MainActivity).showError(onError)
            }

            observe(isLoading) {isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
        }
    }
    companion object {
        const val SPAN_COUNT = 3
        const val MAX_SIZE = 30
        const val INITIAL_POSITION = 0
    }
}
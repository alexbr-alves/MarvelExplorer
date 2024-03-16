package alexProject.fragment.event


import alexProject.adapters.comic.ComicDetailsAdapter
import alexProject.data.model.comic.ComicModelResponse
import alexProject.databinding.FragmentComicEventBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.event.EventComicsViewModel
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
class EventComicsFragment(private val comicId: Int) :
    BaseFragment<FragmentComicEventBinding, EventComicsViewModel>() {

    override val viewModel: EventComicsViewModel by viewModels()
    private val comicDetailsAdapter by lazy { ComicDetailsAdapter() }
    private var totalItems: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentComicEventBinding =
        FragmentComicEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        comicDetailsAdapter.setOnClickListener { comicModel ->
            val action =
                EventDetailsFragmentDirections.actionDetailsEventFragmentToDetailsComicFragment(
                    comicModel
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                EventDetailsFragmentDirections.actionDetailsEventFragmentToComicsFragment(
                    Constants.EVENT,
                    comicId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = comicDetailsAdapter
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

    private fun handleList(result: ResourceState<ComicModelResponse>) {
        result.data?.data?.let { comicModelData ->
            if (comicModelData.result.isEmpty()) {
                binding.textEmpty.setVisible()
            } else {
                comicDetailsAdapter.comics = comicModelData.result.toList()
                totalItems = comicModelData.total
            }
        }
    }
}

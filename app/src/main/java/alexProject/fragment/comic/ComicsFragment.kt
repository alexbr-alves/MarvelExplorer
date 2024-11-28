package alexProject.fragment.comic

import alexProject.activity.MainActivity
import alexProject.adapters.comic.ComicsAdapter
import alexProject.databinding.FragmentComicBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setVisible
import alexProject.viewModel.comics.ComicViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicsFragment : BaseFragment<FragmentComicBinding, ComicViewModel>(), MainActivity.SearchListener {
    override val viewModel: ComicViewModel by viewModels()
    private val comicsAdapter by lazy { ComicsAdapter() }
    private val args: ComicsFragmentArgs by navArgs()
    private var isSearchExpanded = false
    private var totalItems: Int = 0
    private var initialOffset: Int = 0
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentComicBinding =
        FragmentComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setupUI()
        mainActivityObserver()
    }
    private fun mainActivityObserver() = (activity as? MainActivity)?.run {
        observe(searchQueryLiveData) { query ->
            query?.let {
                performSearch(it)
            }
        }
        observe(closedSearchLiveData) { isClosed ->
            if (isClosed) {
                updateComicList()
            }
        }
    }
    private fun collectObserver() = lifecycleScope.launch {
        with(viewModel) {
            getComics(args.origem, args.id, offset = initialOffset)
            observe(onError) { onError ->
                (activity as MainActivity).showError(onError.message)
            }
            observe(onLoading) { isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(comicList) { result ->
                result?.apiResponse?.let { comicModelData ->
                    if (comicModelData.entityList.isNotEmpty()) {
                        if (initialOffset == 0) {
                            comicsAdapter.clearItems()
                        }
                        comicsAdapter.addItems(comicModelData.entityList)
                        totalItems = comicModelData.total
                    }
                }
            }
        }
    }


    private fun setupRecycleView() = with(binding) {
        rvComicList.apply {
            adapter = comicsAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= (totalItemCount / 2) && firstVisibleItemPosition >= 0 && totalItems > initialOffset + 100) {
                        initialOffset += 100
                        binding.progressBarDetail.setVisible(false)
                        viewModel.getComics(args.origem, args.id, offset = initialOffset)
                    }
                }
            })
        }
    }


    private fun setupUI() {
        setupRecycleView()
        comicsAdapter.setOnClickListener {
            val action = ComicsFragmentDirections
                .actionComicsFragmentToDetailsFragment(
                    comic = it,
                    character = Constants.CHARACTER_INVALID,
                    event = Constants.EVENT_INVALID
                )
            findNavController().navigate(action)
        }
    }

    private fun updateComicList(query: String? = null) {
        viewModel.getComics(titleStartsWith = query, offset = initialOffset)
    }
    override fun onSearchQueryChanged(query: String?) {
        query?.let { performSearch(it) }
    }

    private fun performSearch(query: String) {
        initialOffset = 0
        comicsAdapter.clearItems()
        updateComicList(query)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setSearchListener(this)
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.setSearchListener(null)
    }

}
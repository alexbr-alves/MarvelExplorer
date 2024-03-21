package alexProject.fragment.movies

import alexProject.R
import alexProject.activity.MoviesActivity
import alexProject.adapters.ItemSiteMarvelAdapter
import alexProject.data.model.movies.MoviesResponseData
import alexProject.databinding.FragmentMoviesBinding
import alexProject.fragment.BaseFragment
import alexProject.util.observe
import alexProject.util.setVisible
import alexProject.viewModel.movies.MoviesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter by lazy { ItemSiteMarvelAdapter() }
    private val otherMoviesAdapter by lazy { ItemSiteMarvelAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoviesBinding =
        FragmentMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserve()
        clickEvent()
    }

    private fun clickEvent() {
        handleClickEvent(moviesAdapter)
        handleClickEvent(otherMoviesAdapter)
    }

    private fun handleClickEvent(itemAdapter: ItemSiteMarvelAdapter) {
        itemAdapter.setOnItemClickListener { itemModel ->
            (activity as MoviesActivity).setupToolbar(true, itemModel.name)
            val action = MoviesFragmentDirections.actionMoviesFragmentToWebViewFragment(
                itemModel.url,
                itemModel.name
            )
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            fetch()
            observe(isLoading) { isLoading ->
                (activity as MoviesActivity).showProgressBar(isLoading)
            }
            observe(onError) { onError ->
               (activity as MoviesActivity).showError(onError)
            }
            observe(listItems) { result ->
                result.data?.movies.let { moviesResponseData ->
                    moviesResponseData?.let { movies ->
                        loadPage(movies)
                        setupRecycleView(movies)
                        binding.textMarvelMovies.setVisible()
                        binding.textOtherMovies.setVisible()
                    }
                }
            }
        }
    }

    private fun loadPage(moviesResponse: MoviesResponseData) = binding.run {
        Glide.with(image).load(moviesResponse.image).into(image)
        textTitle.text = getString(R.string.title_fragment_movies)
        textDescription.text = moviesResponse.description
    }

    private fun setupRecycleView(data: MoviesResponseData) = with(binding) {
        recycleMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moviesAdapter
            moviesAdapter.list = data.moviesMarvel
        }
        recycleOtherMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = otherMoviesAdapter
            otherMoviesAdapter.list = data.otherMovies
        }
    }

}
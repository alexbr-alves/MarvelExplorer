package alexProject.fragment.movies

import alexProject.R
import alexProject.activity.MoviesActivity
import alexProject.adapters.movies.MarvelMoviesAdapter
import alexProject.adapters.movies.OtherMoviesAdapter
import alexProject.data.model.movies.MoviesResponseData
import alexProject.databinding.FragmentMoviesBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter by lazy { MarvelMoviesAdapter() }
    private val otherMoviesAdapter by lazy { OtherMoviesAdapter() }

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
        moviesAdapter.setOnItemClickListener { moviesModel ->
            val action = MoviesFragmentDirections.actionMoviesFragmentToWebViewFragment(moviesModel.url, moviesModel.name)
            findNavController().navigate(action)
        }
        otherMoviesAdapter.setOnItemClickListener { moviesModel ->
            val action = MoviesFragmentDirections.actionMoviesFragmentToWebViewFragment(moviesModel.url, moviesModel.name)
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.fetch()
        viewModel.listItems.collect { data ->
            when (data) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.setInvisible()
                    data.data?.movies.let { results ->
                        results?.let { movies -> loadPage(movies) }
                        results?.let { setupRecycleView(it) }
                        binding.run {
                            textMarvelMovies.setVisible()
                            textOtherMovies.setVisible()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressbarSearch.setInvisible()
                    data.message?.let { message ->
                        Timber.tag("MoviesFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressbarSearch.setVisible()
                }

                else -> {}
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
            moviesAdapter.movies = data.moviesMarvel
        }
        recycleOtherMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = otherMoviesAdapter
            otherMoviesAdapter.movies = data.otherMovies
        }
    }

}
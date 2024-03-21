package alexProject.fragment.games

import alexProject.R
import alexProject.activity.GamesActivity
import alexProject.adapters.ItemSiteMarvelAdapter
import alexProject.data.model.games.GamesResponseData
import alexProject.databinding.FragmentGamesBinding
import alexProject.fragment.BaseFragment
import alexProject.util.observe
import alexProject.util.setVisible
import alexProject.viewModel.games.GamesViewModel
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
class GamesFragment : BaseFragment<FragmentGamesBinding, GamesViewModel>() {
    override val viewModel: GamesViewModel by viewModels()
    private val gamesAdapter by lazy { ItemSiteMarvelAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGamesBinding = FragmentGamesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserve()
        clickEvent()
    }

    private fun clickEvent() {
        gamesAdapter.setOnItemClickListener { itemModel ->
            (activity as GamesActivity).setupToolbar(true, itemModel.name)
            val action = GamesFragmentDirections.actionGamesFragmentToWebViewFragment3(
                itemModel.url
            )
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            fetch()
            observe(isLoading) { isLoading ->
                binding.progressbarSearch.setVisible(isLoading)
            }
            observe(onError) { onError ->
                (activity as GamesActivity).showError(onError)
            }
            observe(listItems) { result ->
                result.data?.movies.let { gamesResponseData ->
                    gamesResponseData?.let { games ->
                        loadPage(games)
                        setupRecycleView(games)
                        binding.textMarvelGames.setVisible()
                    }
                }
            }
        }
    }
    private fun loadPage(moviesResponse: GamesResponseData) = binding.run {
        Glide.with(image).load(moviesResponse.image).into(image)
        textTitle.text = getString(R.string.title_fragment_games)
        textDescription.text = moviesResponse.description
    }

    private fun setupRecycleView(data: GamesResponseData) = with(binding) {
        recycleGames.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = gamesAdapter
            gamesAdapter.list = data.moviesMarvel
        }
    }

}

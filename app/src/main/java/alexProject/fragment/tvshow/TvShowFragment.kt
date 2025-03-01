package alexProject.fragment.tvshow

import alexProject.activity.TvShowActivity
import alexProject.adapters.ItemSiteMarvelAdapter
import alexProject.data.model.ItemSiteMarvelModel
import alexProject.data.model.tv_show.TvShowModelResponse
import alexProject.data.model.tv_show.TvShowResponseData
import alexProject.databinding.FragmentTvShowBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.observe
import alexProject.util.setVisible
import alexProject.viewModel.tvShow.TvShowViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : BaseFragment<FragmentTvShowBinding, TvShowViewModel>() {
    override val viewModel: TvShowViewModel by viewModels()
    private val marvelDisneyPlusAdapter by lazy { ItemSiteMarvelAdapter() }
    private val nonFictionDisneyAdapter by lazy { ItemSiteMarvelAdapter() }
    private val seriesOnDisneyPlusAdapter by lazy { ItemSiteMarvelAdapter() }
    private val onHuluAdapter by lazy { ItemSiteMarvelAdapter() }
    private val animationAdapter by lazy { ItemSiteMarvelAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTvShowBinding =
        FragmentTvShowBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserve()
        clickEvent()
    }

    private fun clickEvent() {
        handleClickEvent(marvelDisneyPlusAdapter)
        handleClickEvent(nonFictionDisneyAdapter)
        handleClickEvent(seriesOnDisneyPlusAdapter)
        handleClickEvent(onHuluAdapter)
        handleClickEvent(animationAdapter)
    }

    private fun handleClickEvent(itemAdapter: ItemSiteMarvelAdapter) {
        itemAdapter.setOnItemClickListener { itemModel ->
            (activity as TvShowActivity).setupToolbar(true, itemModel.name)
            val action = TvShowFragmentDirections.actionTvShowFragmentToWebViewFragment2(
                itemModel.url,
                itemModel.name
            )
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            getTvShow()
            observe(onError) { onError ->
                (activity as TvShowActivity).showError(onError.message)
            }
            observe(onLoading) { isLoading ->
                binding.progressbarSearch.setVisible(isLoading)
            }
            observe(listItems) { result ->
                handleList(result)
            }
        }
    }

    private fun handleList(result: TvShowModelResponse) {
        result.tvShows.let { tvShowResponseData ->
            tvShowResponseData.let {
                loadPage(it)
                setupRecyclerView(it)
                binding.run {
                    textOnHulu.setVisible()
                    textAnimation.setVisible()
                    textSeriesOnDisneyPlus.setVisible()
                    textNonFictionDisney.setVisible()
                    textMarvelDisneyPlus.setVisible()
                }
            }
        }
    }

    private fun loadPage(moviesResponse: TvShowResponseData) = binding.run {
        Glide.with(image).load(moviesResponse.image).into(image)
        textTitle.text = moviesResponse.name
        textDescription.text = moviesResponse.description
    }

    private fun setupRecyclerView(data: TvShowResponseData) = with(binding) {
        handleRecyclerView(recycleDisneyPlus, marvelDisneyPlusAdapter, data.marvelOnDisneyPlus)
        handleRecyclerView(
            recycleNonFictionDisney,
            nonFictionDisneyAdapter,
            data.nonFictionDisneyPlus
        )
        handleRecyclerView(
            recycleSeriesOnDisneyPlus,
            seriesOnDisneyPlusAdapter,
            data.marvelSeriesDPlus
        )
        handleRecyclerView(recycleOnHulu, onHuluAdapter, data.marvelOnHulu)
        handleRecyclerView(recycleAnimation, animationAdapter, data.animation)
    }

    private fun handleRecyclerView(
        recyclerView: RecyclerView,
        itemAdapter: ItemSiteMarvelAdapter,
        data: List<ItemSiteMarvelModel>
    ) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = itemAdapter
            itemAdapter.list = data
        }
    }

}
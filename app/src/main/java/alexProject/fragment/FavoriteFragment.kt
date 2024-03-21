package alexProject.fragment

import alexProject.adapters.FavoriteAdapter
import alexProject.databinding.FragmentFavoriteBinding
import alexProject.viewModel.FavoriteViewModel
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.Converts
import alexProject.util.setInvisible
import alexProject.util.setVisible
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val viewModel: FavoriteViewModel by viewModels()
    private val favoriteAdapter by lazy { FavoriteAdapter() }
    private var converts = Converts()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding =
        FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        observer()
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.favorites.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let {
                        binding.tvEmptyList.setInvisible()
                        favoriteAdapter.favorites = it.toList()
                    }
                }

                is ResourceState.Empty -> {
                    binding.tvEmptyList.setVisible()
                }

                else -> {}
            }
        }
    }

    private fun clickAdapter() {
        favoriteAdapter.setOnClickListener { favoriteModel ->
            when (favoriteModel.type) {
                Constants.CHARACTER -> {
                    val action = FavoriteFragmentDirections
                        .actionFavoriteCharacterFragmentToDetailsFragment(
                            comic = Constants.COMIC_INVALID,
                            character = converts.favoriteToCharacter(favoriteModel),
                            event = Constants.EVENT_INVALID,
                            )
                    findNavController().navigate(action)
                }

                Constants.COMIC -> {
                    val action = FavoriteFragmentDirections
                        .actionFavoriteCharacterFragmentToDetailsFragment(
                            comic = converts.favoriteToComic(favoriteModel),
                            character = Constants.CHARACTER_INVALID,
                            event = Constants.EVENT_INVALID,
                            )
                    findNavController().navigate(action)
                }

                Constants.EVENT -> {
                    val action = FavoriteFragmentDirections
                        .actionFavoriteCharacterFragmentToDetailsFragment(
                            comic = Constants.COMIC_INVALID,
                            character = Constants.CHARACTER_INVALID,
                            event = converts.favoriteToEvent(favoriteModel),
                        )
                    findNavController().navigate(action)
                }
            }
        }

    }

    private fun setupRecycleView() = with(binding) {
        rvFavoriteCharacter.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(itemTouchHelperCallBack()).attachToRecyclerView(rvFavoriteCharacter)
    }

    private fun itemTouchHelperCallBack(): ItemTouchHelper.Callback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val character = favoriteAdapter.getCharacterPosition(viewHolder.adapterPosition)
                viewModel.delete(character).also {}
            }

        }
    }

}
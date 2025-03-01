package alexProject.fragment.character


import alexProject.activity.MainActivity
import alexProject.adapters.character.CharacterAdapter
import alexProject.data.SharedPreferences
import alexProject.databinding.FragmentCharacterBinding
import alexProject.fragment.BaseFragment
import alexProject.viewModel.character.CharacterViewModel
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setVisible
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding, CharacterViewModel>(),
    MainActivity.SearchListener {
    override val viewModel by viewModels<CharacterViewModel>()
    private val characterAdapter by lazy { CharacterAdapter() }
    private val args: CharacterFragmentArgs by navArgs()
    private var isSearchExpanded = false
    private var totalItems: Int = 0
    private var initialOffset: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterBinding =
        FragmentCharacterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        collectObserve()
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
                updateCharacterList()
            }
        }
    }

    private fun collectObserve() = with(viewModel) {
        fetch(args.origem, args.id, offset = initialOffset)
        observe(characterList) { result ->
            result.let { character ->
                if (character.isNotEmpty()) {
                    if (initialOffset == 0) {
                        characterAdapter.clearItems()
                    }
                    characterAdapter.addItems(character)
                    totalItems = character.count()
                }
            }
        }
        observe(onError) { onError ->
            (activity as MainActivity).showError(onError.message)
        }
        observe(onLoading) { isLoading ->
            binding.progressCircular.setVisible(isLoading)
        }
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action = CharacterFragmentDirections
                .actionCharacterFragmentToDetailsFragment(
                    comic = Constants.COMIC_INVALID,
                    character = characterModel,
                    event = Constants.EVENT_INVALID
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvCharacterList.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = characterAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= (totalItemCount - 3) && firstVisibleItemPosition >= 0) {
                        viewModel.refreshScroll()
                    }
                }
            })
        }
    }



    private fun updateCharacterList(query: String? = null) {
        viewModel.fetch(nameStartsWith = query, offset = initialOffset)
    }

    override fun onSearchQueryChanged(query: String?) {
        query?.let { performSearch(it) }
    }

    private fun performSearch(query: String) {
        initialOffset = 0
        characterAdapter.clearItems()
        updateCharacterList(query)
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
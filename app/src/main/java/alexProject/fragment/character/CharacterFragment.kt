package alexProject.fragment.character


import alexProject.activity.MainActivity
import alexProject.adapters.character.CharacterAdapter
import alexProject.databinding.FragmentCharacterBinding
import alexProject.fragment.BaseFragment
import alexProject.network.response.ErrorResponse
import alexProject.viewModel.character.CharacterViewModel
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.util.toast
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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

    private fun collectObserve() =
        with(viewModel) {
            fetch(args.origem, args.id, offset = initialOffset)
            observe(listItem) { result ->
                result.data.let { character ->
                    if (character.results.isNotEmpty()) {
                        if (initialOffset == 0) {
                            characterAdapter.clearItems()
                        }
                        characterAdapter.addItems(character.results)
                        totalItems = character.total
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
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
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
                        viewModel.fetch(args.origem, args.id, offset = initialOffset)
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
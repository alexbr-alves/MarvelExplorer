package alexProject.fragment.character


import alexProject.activity.MainActivity
import alexProject.adapters.character.CharacterAdapter
import alexProject.databinding.FragmentCharacterBinding
import alexProject.fragment.BaseFragment
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
class CharacterFragment : BaseFragment<FragmentCharacterBinding, CharacterViewModel>() {
    override val viewModel: CharacterViewModel by viewModels()
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
        handleSearchAnimation()
        searchInit()
        setupUI()
    }

    private fun setupUI() {
        if (args.origem == Constants.HOME) {
            binding.searchIcon.setVisible()
        } else {
            binding.searchIcon.setGone()
        }
    }

    private fun handleSearchAnimation() = binding.run {
        searchIcon.setOnClickListener {
            if (isSearchExpanded) {
                collapseSearchBar()
            } else {
                expandSearchBar()
            }
        }
    }

    private fun expandSearchBar() = binding.run {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            val params = searchEditText.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentWidth = fraction
            searchEditText.layoutParams = params
        }
        animator.duration = 300
        animator.start()

        searchEditText.visibility = View.VISIBLE
        searchEditText.requestFocus()

        isSearchExpanded = true
    }

    private fun collapseSearchBar() = binding.run {
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            val params = searchEditText.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentWidth = fraction
            searchEditText.layoutParams = params
        }
        animator.duration = 300
        animator.start()

        searchEditText.visibility = View.GONE
        isSearchExpanded = false
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            fetch(args.origem, args.id, offset = initialOffset)
            observe(listItem) { result ->
                result.data?.data?.let { character ->
                    if (character.results.isNotEmpty()) {
                        characterAdapter.addItems(character.results)
                        totalItems = character.total
                    }
                }
            }
            observe(onError) { onError ->
                (activity as MainActivity).showError(onError)
            }
            observe(isLoading) { isLoading ->
                binding.progressCircular.setVisible(isLoading)
            }
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

    private fun searchInit(query: String? = null) = with(binding) {
        searchEditText.setText(query)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                char: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                char: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (char.toString().isEmpty()) {
                    updateCharacterList()
                } else {
                    updateCharacterList(char.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateCharacterList(query: String? = null) {
        viewModel.fetch(nameStartsWith = query, offset = initialOffset)
    }

}
package alexProject.fragment.comic

import alexProject.adapters.comic.ComicsAdapter
import alexProject.databinding.FragmentComicBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.comics.ComicViewModel
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import timber.log.Timber

@AndroidEntryPoint
class ComicsFragment : BaseFragment<FragmentComicBinding, ComicViewModel>() {
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
        setupRecycleView()
        collectObserver()
        clickAdapter()
        searchInit()
        handleSearchAnimation()
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

    private fun collectObserver() = lifecycleScope.launch {
        with(viewModel) {
            fetch(args.origem, args.id, offset = initialOffset)
            observe(onError) { onError ->
                Timber.e(onError)
            }
            observe(isLoading) { isLoading ->
                binding.progressBarDetail.setVisible(isLoading)
            }
            observe(listItems) { result ->
                result.data?.data?.let { comicModelData ->
                    if (comicModelData.result.isNotEmpty()) {
                        comicsAdapter.comicList = comicModelData.result.toList()
                        totalItems = comicModelData.total
                    }
                }
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvComicList.apply {
            adapter = comicsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItems > initialOffset + 100) {
                        textSeeMore.setVisible()
                    } else {
                        textSeeMore.setGone()
                    }
                }
            })
        }
    }

    private fun clickAdapter() {
        comicsAdapter.setOnClickListener {
            val action = ComicsFragmentDirections
                .actionComicsFragmentToDetailsComicFragment(it)
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            if (totalItems > (initialOffset + 100)) {
                initialOffset += 100
                viewModel.fetch(args.origem, args.id, offset = initialOffset)
                binding.rvComicList.smoothScrollToPosition(0)
            } else {
                binding.textSeeMore.setGone()
            }
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
                    updateComicList()
                } else {
                    updateComicList(char.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateComicList(query: String? = null) {
        viewModel.fetch(titleStartsWith = query, offset = initialOffset)
    }
}
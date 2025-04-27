package alexProject.fragment.event

import alexProject.activity.MainActivity
import alexProject.adapters.event.EventAdapter
import alexProject.data.model.event.EventModel
import alexProject.databinding.FragmentEventBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.event.EventViewModel
import android.animation.ValueAnimator
import android.os.Bundle
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

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>() {
    override val viewModel: EventViewModel by viewModels()
    private val eventAdapter by lazy { EventAdapter() }
    private val args: EventFragmentArgs by navArgs()
    private var isSearchExpanded = false
    private var totalItems: Int = 0
    private var initialOffset: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEventBinding =
        FragmentEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setupRecycleView()
        clickAdapter()
        handleSearchAnimation()
        setupUI()
    }

    private fun setupUI() {
        if (args.verity == Constants.VERIFIED) {
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

    private fun clickAdapter() {
        eventAdapter.setOnClickListener {
            val action = EventFragmentDirections
                .actionEventFragmentToDetailsFragment(
                    event = it,
                    comic = Constants.COMIC_INVALID,
                    character = Constants.CHARACTER_INVALID
                )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            if (totalItems > (initialOffset + 30)) {
                initialOffset += 30
                viewModel.fetch(verify = args.verity, id = args.id, offset = initialOffset)
                binding.rvEventList.smoothScrollToPosition(0)
            } else {
                binding.textSeeMore.setGone()
            }
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        with(viewModel) {
            fetch(args.verity, args.id, offset = initialOffset)
            observe(onError) { onError ->
                (activity as MainActivity).showError(onError.message)
            }
            observe(onLoading) { onLoading ->
                binding.progressBar.setVisible(onLoading)
            }
            observe(listItems) { result ->
                handleList(result)

            }
        }
    }

    private fun handleList(result: List<EventModel>) {
        result.let { eventModelData ->
            if (eventModelData.isNotEmpty()) {
                eventAdapter.eventList = eventModelData
                totalItems = eventModelData.count()
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvEventList.apply {
            adapter = eventAdapter
            layoutManager = GridLayoutManager(context, 2)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItems > initialOffset + 30) {
                        textSeeMore.setVisible()
                    } else {
                        textSeeMore.setGone()
                    }
                }
            })
        }
    }
}
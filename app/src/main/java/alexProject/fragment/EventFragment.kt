package alexProject.fragment

import alexProject.adapters.EventAdapter
import alexProject.databinding.FragmentEventBinding
import alexProject.state.ResourceState
import alexProject.util.Constants
import alexProject.util.setGone
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.EventViewModel
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>() {
    override val viewModel : EventViewModel by viewModels()
    private val eventAdapter by lazy { EventAdapter() }
    private val args : EventFragmentArgs by navArgs()
    private var isSearchExpanded = false
    private var totalItems : Int = 0
    private var initialOffset : Int = 0

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventBinding =
        FragmentEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setupRecycleView()
        clickAdapter()
        searchInit()
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
                .actionEventFragmentToDetailsEventFragment(it)
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            if (totalItems > (initialOffset + 100)) {
                viewModel.fetch(args.verity, args.id, offset = initialOffset + 100)
                initialOffset += 100
                binding.rvEventList.smoothScrollToPosition(0)
            } else {
                binding.textSeeMore.setGone()
            }
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.fetch(args.verity, args.id, offset = initialOffset)
        viewModel.eventList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBar.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            eventAdapter.eventList = values.data.result.toList()
                            totalItems = values.data.result.size
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBar.setInvisible()
                    resource.message?.let { message ->
                        Timber.tag("EventFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBar.setVisible()
                }

                else -> {}
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvEventList.apply {
            adapter = eventAdapter
            layoutManager = GridLayoutManager(context, 2)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
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

    private fun searchInit(query : String? = null) = with(binding) {
        searchEditText.setText(query)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                char : CharSequence?,
                start : Int,
                count : Int,
                after : Int
            ) {
            }

            override fun onTextChanged(
                char : CharSequence?,
                start : Int,
                before : Int,
                count : Int
            ) {
                if (char.toString().isEmpty()) {
                    updateEventList()
                } else {
                    updateEventList(char.toString())
                }
            }

            override fun afterTextChanged(s : Editable?) {}
        })
    }

    private fun updateEventList(query : String? = null) {
        viewModel.fetch(titleStartsWith = query, offset = initialOffset)
    }
}
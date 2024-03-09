package alexProject.fragment.group

import alexProject.adapters.group.GroupAdapter
import alexProject.databinding.FragmentGroupBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.setInvisible
import alexProject.util.setVisible
import alexProject.viewModel.group.GroupViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding, GroupViewModel>() {
    override val viewModel: GroupViewModel by viewModels()
    private val groupAdapter by lazy { GroupAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGroupBinding =
        FragmentGroupBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserve()
        clickAdapter()
    }

    private fun clickAdapter() {
        groupAdapter.setOnClickListener {
            it
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.fetch()
        viewModel.listItems.collect { result ->
            when(result) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.setInvisible()
                    result.data?.groups.let {
                        if (!it.isNullOrEmpty()) {
                            groupAdapter.groups = it.toList()
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressbarSearch.setInvisible()
                    result.message?.let { message ->
                        Timber.tag("GroupFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressbarSearch.setVisible()
                }

                else -> {}
            }

        }
    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}
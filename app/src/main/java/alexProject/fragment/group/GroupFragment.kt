package alexProject.fragment.group

import alexProject.activity.GroupsActivity
import alexProject.adapters.group.GroupAdapter
import alexProject.data.model.group.GroupModelResponse
import alexProject.databinding.FragmentGroupBinding
import alexProject.fragment.BaseFragment
import alexProject.state.ResourceState
import alexProject.util.observe
import alexProject.util.setVisible
import alexProject.viewModel.group.GroupViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        groupAdapter.setOnClickListener { groupModel ->
            val action = GroupFragmentDirections.actionGroupFragmentToGroupWikiFragment(groupModel)
            findNavController().navigate(action)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            getGroups()
            observe(onError) { onError ->
                (activity as GroupsActivity).showError(onError.message)
            }
            observe(onLoading) { isLoading ->
                binding.progressbarSearch.setVisible(isLoading)
            }
            observe(listItems) { result ->
                handleList(result)
            }
        }
    }

    private fun handleList(result: GroupModelResponse) {
        result.let { groupModelResponse ->
            if (groupModelResponse.groups.isNotEmpty()) {
                groupAdapter.groups = groupModelResponse.groups.toList()
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
package alexProject.fragment.group

import alexProject.activity.MainActivity
import alexProject.adapters.group.CurrentMemberAdapter
import alexProject.adapters.group.FormerMemberAdapter
import alexProject.data.model.group.GroupModel
import alexProject.databinding.FragmentGroupWikiBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

class GroupWikiFragment : Fragment() {
    private var binding: FragmentGroupWikiBinding? = null
    private val args: GroupWikiFragmentArgs by navArgs()
    private lateinit var groupModel: GroupModel
    private val currentMemberAdapter by lazy { CurrentMemberAdapter() }
    private val formerMemberAdapter by lazy { FormerMemberAdapter() }
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupWikiBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupModel = args.groupModel
        loadGroupWiki(groupModel)
        setupUI()
        setupRecycler()
    }

    private fun setupRecycler() = binding?.run {
        recycleCurrentMembers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = currentMemberAdapter
            currentMemberAdapter.groups = groupModel.currentMembers
        }

        recycleFormerMembers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = formerMemberAdapter
            formerMemberAdapter.groups = groupModel.formerMembers
        }

    }

    private fun setupUI() {
        mainActivity.setupToolbar(true, groupModel.name)
    }

    private fun loadGroupWiki(groupModel: GroupModel) = binding?.run {
        Glide.with(imageGroup)
            .load(groupModel.image)
            .into(imageGroup)
        textTitle.text = groupModel.name
        textDescription.text = groupModel.description


    }

}
package alexProject.adapters

import alexProject.data.model.StoryModelData
import alexProject.fragment.StoryFragment
import alexProject.fragment.StoryViewPagerFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class StoryViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var storyModel: StoryModelData
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
        private var positionViewpager: Int = 0

    override fun getItemCount(): Int = storyModel.data.size

    override fun createFragment(position: Int): Fragment {
        positionViewpager = position
        val storyData = storyModel.data[position]
        val fragmentId = storyData.id
        return StoryViewPagerFragment(storyModel = storyData, id = fragmentId)
    }

}
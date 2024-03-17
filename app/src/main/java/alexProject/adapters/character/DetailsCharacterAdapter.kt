package alexProject.adapters.character


import alexProject.fragment.character.ComicsViewPagerFragment
import alexProject.fragment.character.DescriptionViewPagerFragment
import alexProject.fragment.character.EventsViewPagerFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCharacterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionViewPagerFragment(description)
            1 -> ComicsViewPagerFragment(id)
            2 -> EventsViewPagerFragment(id)
            else -> ComicsViewPagerFragment(id)
        }
    }
}
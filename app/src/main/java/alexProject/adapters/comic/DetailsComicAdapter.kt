package alexProject.adapters.comic

import alexProject.fragment.character.DescriptionViewPagerFragment
import alexProject.fragment.character.EventsViewPagerFragment
import alexProject.fragment.comic.CharactersViewPagerFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsComicAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionViewPagerFragment(description)
            1 -> CharactersViewPagerFragment(id)
            2 -> EventsViewPagerFragment(id)
            else -> DescriptionViewPagerFragment(description)
        }
    }
}
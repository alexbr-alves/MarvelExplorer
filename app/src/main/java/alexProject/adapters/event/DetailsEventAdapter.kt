package alexProject.adapters.event

import alexProject.fragment.event.EventCharactersFragment
import alexProject.fragment.event.EventComicsFragment
import alexProject.fragment.event.EventDescriptionFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsEventAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventDescriptionFragment(description)
            1 -> EventCharactersFragment(id)
            2 -> EventComicsFragment(id)
            else -> EventDescriptionFragment(description)
        }
    }
}
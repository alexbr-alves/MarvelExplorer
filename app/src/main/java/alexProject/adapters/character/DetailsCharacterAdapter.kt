package alexProject.adapters.character


import alexProject.fragment.comic.ComicsViewPagerFragment
import alexProject.fragment.DescriptionViewPagerFragment
import alexProject.fragment.event.EventsViewPagerFragment
import alexProject.fragment.character.CharactersViewPagerFragment
import alexProject.util.Constants
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCharacterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String, val origin: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (origin) {
            Constants.CHARACTER -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> ComicsViewPagerFragment(id, origin)
                    2 -> EventsViewPagerFragment(id, origin)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            Constants.COMIC -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> CharactersViewPagerFragment(id, origin)
                    2 -> EventsViewPagerFragment(id, origin)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            Constants.EVENT -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> CharactersViewPagerFragment(id, origin)
                    2 -> ComicsViewPagerFragment(id, origin)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            else -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> ComicsViewPagerFragment(id, origin)
                    2 -> EventsViewPagerFragment(id, origin)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
        }
    }
}
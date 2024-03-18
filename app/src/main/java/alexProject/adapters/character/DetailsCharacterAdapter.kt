package alexProject.adapters.character


import alexProject.fragment.character.ComicsViewPagerFragment
import alexProject.fragment.character.DescriptionViewPagerFragment
import alexProject.fragment.character.EventsViewPagerFragment
import alexProject.fragment.comic.CharactersViewPagerFragment
import alexProject.util.Constants
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCharacterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String, private val origin: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (origin) {
            Constants.CHARACTER -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> ComicsViewPagerFragment(id)
                    2 -> EventsViewPagerFragment(id)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            Constants.COMIC -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> CharactersViewPagerFragment(id)
                    2 -> EventsViewPagerFragment(id)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            Constants.EVENT -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> CharactersViewPagerFragment(id)
                    2 -> ComicsViewPagerFragment(id)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
            else -> {
                when (position) {
                    0 -> DescriptionViewPagerFragment(description)
                    1 -> ComicsViewPagerFragment(id)
                    2 -> EventsViewPagerFragment(id)
                    else -> DescriptionViewPagerFragment(description)
                }
            }
        }
    }
}
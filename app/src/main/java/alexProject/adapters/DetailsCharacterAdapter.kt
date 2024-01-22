package alexProject.adapters


import alexProject.fragment.detailsCharacter.CharacterComicsFragment
import alexProject.fragment.detailsCharacter.CharacterDescriptionFragment
import alexProject.fragment.detailsCharacter.CharacterEventsFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCharacterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharacterDescriptionFragment(description)
            1 -> CharacterComicsFragment(id)
            2 -> CharacterEventsFragment(id)
            else -> CharacterComicsFragment(id)
        }
    }
}
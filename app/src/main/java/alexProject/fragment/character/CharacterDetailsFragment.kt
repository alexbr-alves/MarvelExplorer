package alexProject.fragment.character

import alexProject.R
import alexProject.activity.MainActivity
import alexProject.data.model.character.CharacterModel
import alexProject.databinding.FragmentDetailsCharacterBinding
import alexProject.adapters.character.DetailsCharacterAdapter
import alexProject.data.model.FavoriteModel
import alexProject.fragment.BaseFragment
import alexProject.util.Converts
import alexProject.viewModel.character.CharacterDetailsViewModel
import alexProject.util.loadImage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment :
    BaseFragment<FragmentDetailsCharacterBinding, CharacterDetailsViewModel>() {
    override val viewModel : CharacterDetailsViewModel by viewModels()
    private val args : CharacterDetailsFragmentArgs by navArgs()
    private lateinit var characterModel : CharacterModel
    private var viewPager2 : ViewPager2? = null
    private lateinit var favoriteModel : FavoriteModel
    private var converts = Converts()

    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        favoriteModel = converts.characterToFavorite(characterModel)
        descriptionCharacter()
        onLoadCharacter(characterModel)
        viewModel.searchFavorite(favoriteModel.id)
        handleViewPager()
        handleTabLayout()
        setupUI()
    }

    private fun setupUI() = binding.run {
        handleBadge(binding.tabLayout.getTabAt(1)!!)
        handleBadge(binding.tabLayout.getTabAt(2)!!)

    }

    private fun handleBadge(tab : TabLayout.Tab) {
        val badge = tab.orCreateBadge
        badge.isVisible = true
        badge.backgroundColor = resources.getColor(R.color.lavender_indigo)
        badge.horizontalOffset = 20
        when (tab.position) {
            1 -> {
                badge.number = characterModel.comics.available ?: 0
            }
            2 -> {
                badge.number = characterModel.events.available ?: 0
            }
            else -> {
                badge.isVisible = false
            }
        }
    }

    private fun handleTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab : TabLayout.Tab) {
                val selectedTabPosition = tab.position
                binding.viewPager2.setCurrentItem(selectedTabPosition, true)
            }
            override fun onTabUnselected(tab : TabLayout.Tab) {}
            override fun onTabReselected(tab : TabLayout.Tab) {}
        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun handleViewPager() {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
        val characterDescription =
            if (!characterModel.description.isNullOrEmpty()) characterModel.description else ""
        val detailsCharacterAdapter = DetailsCharacterAdapter(
            childFragmentManager,
            lifecycle,
            characterModel.id,
            characterDescription
        )
        viewPager2?.adapter = detailsCharacterAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
            }
        })
    }

    private fun descriptionCharacter() = binding.run {
        imageFavorite.setOnClickListener {
            viewModel.searchFavorite(favoriteModel.id)
            viewModel.searchCharacter.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.delete(favoriteModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite)
                } else {
                    viewModel.insert(favoriteModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite_complete)
                }
            }
        }
    }

    private fun onLoadCharacter(characterModel : CharacterModel) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_complete else R.drawable.favorite
            (activity as MainActivity).setupToolbar(true, characterModel.name)
            imageFavorite.setImageResource(color)
           handleTabLayout()
            loadImage(
                image,
                characterModel.thumbnailModel.path,
                characterModel.thumbnailModel.extension
            )
        }
    }

}
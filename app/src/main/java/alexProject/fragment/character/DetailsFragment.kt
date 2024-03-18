package alexProject.fragment.character

import alexProject.R
import alexProject.data.model.character.CharacterModel
import alexProject.databinding.FragmentDetailsCharacterBinding
import alexProject.adapters.character.DetailsCharacterAdapter
import alexProject.data.model.FavoriteModel
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel
import alexProject.fragment.BaseFragment
import alexProject.util.Constants
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
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment :
    BaseFragment<FragmentDetailsCharacterBinding, CharacterDetailsViewModel>() {
    override val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var characterModel: CharacterModel
    private lateinit var comicModel: ComicModel
    private lateinit var eventModel: EventModel
    private var viewPager2: ViewPager2? = null
    private lateinit var favoriteModel: FavoriteModel
    private var converts = Converts()

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        comicModel = args.comic
        eventModel = args.event
        favoriteModel = converts.characterToFavorite(characterModel)
        descriptionCharacter()
        onLoad()
        viewModel.searchFavorite(favoriteModel.id)
        handleViewPager()
        handleTabLayout()
        setupUI()
    }

    private fun setupUI() = binding.run {
        handleBadge(binding.tabLayout.getTabAt(1)!!)
        handleBadge(binding.tabLayout.getTabAt(2)!!)

    }

    private fun handleBadge(tab: TabLayout.Tab) {
        val badge = tab.orCreateBadge
        badge.isVisible = true
        badge.backgroundColor = resources.getColor(R.color.lavender_indigo)
        badge.horizontalOffset = 20
        handleBadgeCharacter(tab, badge)
        handleBadgeEvent(tab, badge)
        handleBadgeComic(tab, badge)
    }

    private fun handleBadgeCharacter(tab: TabLayout.Tab, badge: BadgeDrawable) {
        if (characterModel != Constants.CHARACTER_INVALID) {
            binding.tabLayout.getTabAt(1)?.text = getString(R.string.title_fragment_comic)
            binding.tabLayout.getTabAt(2)?.text = getString(R.string.title_fragment_event)
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
    }

    private fun handleBadgeComic(tab: TabLayout.Tab, badge: BadgeDrawable) {
        if (comicModel != Constants.COMIC_INVALID) {
            binding.tabLayout.getTabAt(1)?.text = getString(R.string.title_fragment_characters)
            binding.tabLayout.getTabAt(2)?.text = getString(R.string.title_fragment_event)
            when (tab.position) {
                1 -> {
                    badge.number = comicModel.characters.available ?: 0
                }

                2 -> {
                    badge.number = comicModel.events.available ?: 0
                }

                else -> {
                    badge.isVisible = false
                }
            }
        }
    }

    private fun handleBadgeEvent(tab: TabLayout.Tab, badge: BadgeDrawable) {
        if (eventModel != Constants.EVENT_INVALID) {
            binding.tabLayout.getTabAt(1)?.text = getString(R.string.title_fragment_characters)
            binding.tabLayout.getTabAt(2)?.text = getString(R.string.title_fragment_comic)
            when (tab.position) {
                1 -> {
                    badge.number = eventModel.characters.available ?: 0
                }

                2 -> {
                    badge.number = eventModel.comics.available ?: 0
                }

                else -> {
                    badge.isVisible = false
                }
            }
        }
    }


    private fun handleTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedTabPosition = tab.position
                binding.viewPager2.setCurrentItem(selectedTabPosition, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun handleViewPager() {
        handleCharacterViewPager()
        handleComicViewPager()
        handleEventViewPager()
    }

    private fun handleEventViewPager() {
        if (eventModel != Constants.EVENT_INVALID) {
            viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
            val characterDescription =
                if (!eventModel.description.isNullOrEmpty()) eventModel.description else ""
            val detailsCharacterAdapter = DetailsCharacterAdapter(
                fragmentManager = childFragmentManager,
                lifecycle = lifecycle,
                id = eventModel.id,
                description = characterDescription,
                origin = Constants.EVENT
            )
            viewPager2?.adapter = detailsCharacterAdapter
            viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPager2?.offscreenPageLimit = 1
                }
            })
        }
    }

    private fun handleComicViewPager() {
        if (comicModel != Constants.COMIC_INVALID) {
            viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
            val characterDescription =
                if (!comicModel.description.isNullOrEmpty()) comicModel.description else ""
            val detailsCharacterAdapter = DetailsCharacterAdapter(
                childFragmentManager,
                lifecycle,
                comicModel.id,
                characterDescription,
                Constants.COMIC
            )
            viewPager2?.adapter = detailsCharacterAdapter
            viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPager2?.offscreenPageLimit = 1
                }
            })
        }
    }

    private fun handleCharacterViewPager() {
        if (characterModel != Constants.CHARACTER_INVALID) {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
        val characterDescription =
            if (!characterModel.description.isNullOrEmpty()) characterModel.description else ""
        val detailsCharacterAdapter = DetailsCharacterAdapter(
            childFragmentManager,
            lifecycle,
            characterModel.id,
            characterDescription,
            Constants.CHARACTER
        )
        viewPager2?.adapter = detailsCharacterAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
            }
        })
        }
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

    private fun onLoad() {
        onLoadCharacter()
        onLoadComic()
        onLoadEvent()
    }

    private fun onLoadEvent()  = with(binding) {
        if (eventModel != Constants.EVENT_INVALID) {
            viewModel.searchCharacter.observe(viewLifecycleOwner) {
                val color = if (it) R.drawable.favorite_complete else R.drawable.favorite
                imageFavorite.setImageResource(color)
                handleTabLayout()
                loadImage(
                    image,
                    eventModel.thumbnailModel.path,
                    eventModel.thumbnailModel.extension
                )
            }
        }
    }

    private fun onLoadComic() = with(binding) {
        if (comicModel != Constants.COMIC_INVALID) {
            viewModel.searchCharacter.observe(viewLifecycleOwner) {
                val color = if (it) R.drawable.favorite_complete else R.drawable.favorite
                imageFavorite.setImageResource(color)
                handleTabLayout()
                loadImage(
                    image,
                    comicModel.thumbnailModel.path,
                    comicModel.thumbnailModel.extension
                )
            }
        }
    }

    private fun onLoadCharacter() = with(binding) {
        if (characterModel != Constants.CHARACTER_INVALID) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_complete else R.drawable.favorite
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



}
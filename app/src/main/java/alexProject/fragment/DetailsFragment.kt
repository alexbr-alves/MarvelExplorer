package alexProject.fragment

import alexProject.R
import alexProject.activity.MainActivity
import alexProject.data.model.character.CharacterModel
import alexProject.databinding.FragmentDetailsCharacterBinding
import alexProject.adapters.character.DetailsCharacterAdapter
import alexProject.data.model.FavoriteModel
import alexProject.data.model.comic.ComicModel
import alexProject.data.model.event.EventModel
import alexProject.util.Constants.CHARACTER
import alexProject.util.Constants.CHARACTER_INVALID
import alexProject.util.Constants.COMIC
import alexProject.util.Constants.COMIC_INVALID
import alexProject.util.Constants.EVENT
import alexProject.util.Constants.EVENT_INVALID
import alexProject.util.Converts
import alexProject.viewModel.DetailsViewModel
import alexProject.util.loadImage
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()
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
        favoriteModel = getFavorite()
        viewModel.searchFavorite(favoriteModel.id)
        setupUI()
    }

    private fun setupUI() = binding.run {
        setupBadge(binding.tabLayout.getTabAt(TAB_INDEX_1)!!)
        setupBadge(binding.tabLayout.getTabAt(TAB_INDEX_2)!!)
        handleToolbar()
        setupViewPager()
        handleTabLayout()
        setupFavorite()
        onLoad()
    }

    private fun getFavorite(): FavoriteModel {
        if (characterModel == CHARACTER_INVALID && comicModel == COMIC_INVALID) {
            return converts.eventToFavorite(eventModel)
        } else if (characterModel == CHARACTER_INVALID && eventModel == EVENT_INVALID) {
            return converts.comicToFavorite(comicModel)
        } else {
            return converts.characterToFavorite(characterModel)
        }
    }

    private fun handleToolbar() {
        val toolbarTitle =
            if (characterModel == CHARACTER_INVALID && comicModel == COMIC_INVALID) {
                getString(R.string.title_fragment_event)
            } else if (characterModel == CHARACTER_INVALID && eventModel == EVENT_INVALID) {
                getString(R.string.title_fragment_comic)
            } else {
                getString(R.string.title_fragment_characters)
            }
        (activity as MainActivity).setupToolbar(true, toolbarTitle)
    }

    private fun setupBadge(tab: TabLayout.Tab) {
        val badge = tab.orCreateBadge
        badge.isVisible = true
        badge.backgroundColor = resources.getColor(R.color.lavender_indigo)
        badge.horizontalOffset = BADGE_HORIZONTAL_OFFSET

        if (eventModel != EVENT_INVALID) {
            handleBadge(
                tab,
                badge,
                getString(R.string.title_fragment_characters),
                getString(R.string.title_fragment_comic),
                eventModel.characters.available,
                eventModel.comics.available
            )
        } else if (comicModel != COMIC_INVALID) {
            handleBadge(
                tab,
                badge,
                getString(R.string.title_fragment_characters),
                getString(R.string.title_fragment_event),
                comicModel.characters.available,
                comicModel.events.available
            )
        } else {
            handleBadge(
                tab,
                badge,
                getString(R.string.title_fragment_comic),
                getString(R.string.title_fragment_event),
                characterModel.comics.available,
                characterModel.events.available
            )
        }
    }

    private fun handleBadge(
        tab: TabLayout.Tab,
        badge: BadgeDrawable,
        tabOneString: String,
        tabTwoString: String,
        badgeOneNumber: Int?,
        badgeTwoNumber: Int?
    ) {
        binding.tabLayout.getTabAt(TAB_INDEX_1)?.text = tabOneString
        binding.tabLayout.getTabAt(TAB_INDEX_2)?.text = tabTwoString
        when (tab.position) {
            1 -> {
                badge.number = badgeOneNumber ?: 0
            }

            2 -> {
                badge.number = badgeTwoNumber ?: 0
            }

            else -> {
                badge.isVisible = false
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

    private fun setupViewPager() {
        if (eventModel != EVENT_INVALID) {
            handleViewPager(
                eventModel.id,
                eventModel.title,
                eventModel.description,
                EVENT
            )
        } else if (comicModel != COMIC_INVALID) {
            handleViewPager(
                comicModel.id,
                comicModel.title,
                comicModel.description,
                COMIC
            )
        } else {
            handleViewPager(
                characterModel.id,
                characterModel.name,
                characterModel.description,
                CHARACTER
            )
        }
    }

    private fun handleViewPager(
        id: Int,
        title: String,
        description: String,
        origin: String
    ) {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
        val detailsCharacterAdapter = DetailsCharacterAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
            id = id,
            title = title,
            description = description ?: "",
            origin = origin
        )
        viewPager2?.adapter = detailsCharacterAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
            }
        })
    }

    private fun setupFavorite() = binding.run {
        imageFavorite.setOnClickListener {
            handleFavorite()
        }
        image.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0
            override fun onClick(v: View) {
                val clickTime = System.currentTimeMillis()
                if (clickTime - lastClickTime < 300) {
                    handleFavorite()
                }
                lastClickTime = clickTime
            }
        })
    }

    private fun handleFavorite() = binding.run {
        handleFavoriteAnimationLike()
        val isFavorite = imageFavorite.isSelected
        if (isFavorite) {
            viewModel.delete(favoriteModel)
        } else {
            viewModel.insert(favoriteModel)
        }
        binding.imageFavorite.isSelected = !binding.imageFavorite.isSelected

    }

    private fun handleFavoriteAnimationLike() = binding.run {
        if (!imageFavorite.isSelected) {
            imageViewAnimation.likeAnimation()
        }
    }

    private fun onLoad() {
        if (eventModel != EVENT_INVALID) {
            loadModel(
                eventModel.thumbnailModel.path,
                eventModel.thumbnailModel.extension
            )
        } else if (comicModel != COMIC_INVALID) {
            loadModel(
                comicModel.thumbnailModel.path,
                comicModel.thumbnailModel.extension
            )
        } else {
            loadModel(
                characterModel.thumbnailModel.path,
                characterModel.thumbnailModel.extension
            )
        }
    }

    private fun loadModel(
        imagePath: String,
        imageExtension: String,
    ) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            imageFavorite.isSelected = it
            handleTabLayout()
            loadImage(
                image,
                imagePath,
                imageExtension
            )
        }
    }

    companion object {
        const val BADGE_HORIZONTAL_OFFSET = 20
        const val TAB_INDEX_1 = 1
        const val TAB_INDEX_2 = 2
    }
}
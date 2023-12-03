package alexProject.fragment.detailsEvent

import alexProject.R
import alexProject.adapters.DetailsEventAdapter
import alexProject.data.model.FavoriteModel
import alexProject.data.model.event.EventModel
import alexProject.databinding.FragmentDetailsEventBinding
import alexProject.fragment.BaseFragment
import alexProject.util.Converts
import alexProject.util.loadImage
import alexProject.viewModel.detailEvent.EventDetailsViewModel
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
class EventDetailsFragment : BaseFragment<FragmentDetailsEventBinding, EventDetailsViewModel>() {
    override val viewModel : EventDetailsViewModel by viewModels()
    private val args : EventDetailsFragmentArgs by navArgs()
    private lateinit var eventModel : EventModel
    private var viewPagerEvent : ViewPager2? = null
    private lateinit var favoriteModel : FavoriteModel
    private val converts = Converts()

    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsEventBinding =
        FragmentDetailsEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventModel = args.eventModel
        favoriteModel = converts.eventToFavorite(eventModel)
        viewModel.searchFavorite(favoriteModel.id)
        handleViewPager()
        onLoadEvent(eventModel)
        descriptionEvent()
        handleTabLayout()
        setupUI()
    }

    private fun setupUI() = binding.run {
        binding.iconBackAlone.imageBack.setOnClickListener {
            activity?.onBackPressed()
        }
        handleBadge(binding.tabLayout.getTabAt(1)!!)
        handleBadge(binding.tabLayout.getTabAt(2)!!)
    }

    private fun handleBadge(tab : TabLayout.Tab) {
        val badge = tab.orCreateBadge
        badge.isVisible = true
        badge.backgroundColor = resources.getColor(R.color.lavender_indigo)
        when (tab.position) {
            1 -> {
                badge.number = eventModel.characters.available
            }
            2 -> {
                badge.number = eventModel.comics.available
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
                binding.viewPagerEvent.setCurrentItem(selectedTabPosition, true)
            }
            override fun onTabUnselected(tab : TabLayout.Tab) {}
            override fun onTabReselected(tab : TabLayout.Tab) {}
        })
        binding.viewPagerEvent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun onLoadEvent(comic : EventModel) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_complete else R.drawable.favorite
            binding.imageFavorite.setImageResource(color)
            textName.text = comic.title
            loadImage(
                imageEvent, comic.thumbnailModel.path, comic.thumbnailModel.extension
            )
        }
    }

    private fun descriptionEvent() = binding.run {
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

    private fun handleViewPager() {
        viewPagerEvent = view?.findViewById<ViewPager2>(R.id.viewPagerEvent)
        val comicDescription =
            if (!eventModel.description.isNullOrEmpty()) eventModel.description else ""
        val detailsComicAdapter =
            DetailsEventAdapter(childFragmentManager, lifecycle, eventModel.id, comicDescription)
        viewPagerEvent?.adapter = detailsComicAdapter
        viewPagerEvent?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                viewPagerEvent?.offscreenPageLimit = 1
            }
        })
    }

}
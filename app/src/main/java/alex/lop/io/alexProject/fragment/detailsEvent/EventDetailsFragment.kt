package alex.lop.io.alexProject.fragment.detailsEvent

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.DetailsEventAdapter
import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.databinding.FragmentDetailsEventBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.util.Converts
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.viewModel.detailEvent.EventDetailsViewModel
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

    private fun setupUI() {
        binding.iconBackAlone.imageBack.setOnClickListener {
            activity?.onBackPressed()
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
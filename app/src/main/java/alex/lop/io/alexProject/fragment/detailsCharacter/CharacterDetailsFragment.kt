package alex.lop.io.alexProject.fragment.detailsCharacter

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.databinding.FragmentDetailsCharacterBinding
import alex.lop.io.alexProject.adapters.DetailsCharacterAdapter
import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.util.Converts
import alex.lop.io.alexProject.viewModel.detailCharacter.CharacterDetailsViewModel
import alex.lop.io.alexProject.util.loadImage
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

    private fun setupUI() {
        binding.iconBackAlone.imageBack.setOnClickListener {
            activity?.onBackPressed()
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
            binding.imageFavorite.setImageResource(color)
            tvNameCharacterDetails.text = characterModel.name
            loadImage(
                imgCharacterDetails,
                characterModel.thumbnailModel.path,
                characterModel.thumbnailModel.extension
            )
        }
    }

}
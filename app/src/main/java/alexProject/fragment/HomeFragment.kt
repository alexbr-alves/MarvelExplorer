package alexProject.fragment

import alexProject.R
import alexProject.data.local.DataHome
import alexProject.databinding.FragmentHomeBinding
import alexProject.util.Constants
import alexProject.util.loadImage
import alexProject.viewModel.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel : HomeViewModel by viewModels()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvents()
        setupUI()
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    private fun setupUI() {
        loadCharacters()
    }

    private fun loadCharacters() = binding.run {
        loadImage(
            imageCharacter1,
            DataHome.CHARACTER1.thumbnailModel.path,
            DataHome.CHARACTER1.thumbnailModel.extension
        )
        loadImage(
            imageCharacter2,
            DataHome.CHARACTER2.thumbnailModel.path,
            DataHome.CHARACTER2.thumbnailModel.extension
        )
        loadImage(
            imageCharacter3,
            DataHome.CHARACTER3.thumbnailModel.path,
            DataHome.CHARACTER3.thumbnailModel.extension
        )
        loadImage(
            imageCharacter4,
            DataHome.CHARACTER4.thumbnailModel.path,
            DataHome.CHARACTER4.thumbnailModel.extension
        )
        loadImage(
            imageCharacter5,
            DataHome.CHARACTER5.thumbnailModel.path,
            DataHome.CHARACTER5.thumbnailModel.extension
        )
    }


    private fun clickEvents() = with(binding) {
        imageCharacter1.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsCharacterFragment(DataHome.CHARACTER1)
            findNavController().navigate(action)
        }

        imageCharacter2.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsCharacterFragment(DataHome.CHARACTER2)
            findNavController().navigate(action)
        }

        imageCharacter3.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsCharacterFragment(DataHome.CHARACTER3)
            findNavController().navigate(action)
        }

        imageCharacter4.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsCharacterFragment(DataHome.CHARACTER4)
            findNavController().navigate(action)
        }

        imageCharacter5.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsCharacterFragment(DataHome.CHARACTER5)
            findNavController().navigate(action)
        }

        textTeams.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupFragment()
            findNavController().navigate(action)
        }

        textCharacters.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListCharacterFragment(Constants.HOME, 0)
            findNavController().navigate(action)
        }
        textComics.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToComicsFragment(Constants.HOME, 0)
            findNavController().navigate(action)
        }
        textEvents.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEventFragment2(Constants.HOME, 0)
            findNavController().navigate(action)
        }


    }

}
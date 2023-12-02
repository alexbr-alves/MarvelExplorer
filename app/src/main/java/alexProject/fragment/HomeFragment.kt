package alexProject.fragment

import alexProject.data.local.DataHome
import alexProject.databinding.FragmentHomeBinding
import alexProject.util.loadImage
import alexProject.viewModel.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        loadComics()
        loadEvents()
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

    private fun loadComics() = binding.run {
        loadImage(
            imageComic1,
            DataHome.COMIC1.thumbnailModel.path,
            DataHome.COMIC1.thumbnailModel.extension
        )
        loadImage(
            imageComic2,
            DataHome.COMIC2.thumbnailModel.path,
            DataHome.COMIC2.thumbnailModel.extension
        )
        loadImage(
            imageComic3,
            DataHome.COMIC3.thumbnailModel.path,
            DataHome.COMIC3.thumbnailModel.extension
        )
        loadImage(
            imageComic4,
            DataHome.COMIC4.thumbnailModel.path,
            DataHome.COMIC4.thumbnailModel.extension
        )
        loadImage(
            imageComic5,
            DataHome.COMIC5.thumbnailModel.path,
            DataHome.COMIC5.thumbnailModel.extension
        )
    }

    private fun loadEvents() = binding.run {
        loadImage(
            imageEvent1,
            DataHome.EVENT1.thumbnailModel.path,
            DataHome.EVENT1.thumbnailModel.extension
        )
        loadImage(
            imageEvent2,
            DataHome.EVENT2.thumbnailModel.path,
            DataHome.EVENT2.thumbnailModel.extension
        )
        loadImage(
            imageEvent3,
            DataHome.EVENT3.thumbnailModel.path,
            DataHome.EVENT3.thumbnailModel.extension
        )
        loadImage(
            imageEvent4,
            DataHome.EVENT4.thumbnailModel.path,
            DataHome.EVENT4.thumbnailModel.extension
        )
        loadImage(
            imageEvent5,
            DataHome.EVENT5.thumbnailModel.path,
            DataHome.EVENT5.thumbnailModel.extension
        )
    }

    private fun clickEvents() = with(binding) {
        layoutCharacter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListCharacterFragment()
            findNavController().navigate(action)
        }
        layoutComic.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToComicsFragment()
            findNavController().navigate(action)
        }
        layoutEvent.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEventFragment2()
            findNavController().navigate(action)
        }
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


        imageComic1.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsComicFragment(DataHome.COMIC1)
            findNavController().navigate(action)
        }
        imageComic2.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsComicFragment(DataHome.COMIC2)
            findNavController().navigate(action)
        }
        imageComic3.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsComicFragment(DataHome.COMIC3)
            findNavController().navigate(action)
        }
        imageComic4.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsComicFragment(DataHome.COMIC4)
            findNavController().navigate(action)
        }
        imageComic5.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsComicFragment(DataHome.COMIC5)
            findNavController().navigate(action)
        }


        imageEvent1.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsEventFragment(DataHome.EVENT1)
            findNavController().navigate(action)
        }
        imageEvent2.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsEventFragment(DataHome.EVENT2)
            findNavController().navigate(action)
        }
        imageEvent3.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsEventFragment(DataHome.EVENT3)
            findNavController().navigate(action)
        }
        imageEvent4.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsEventFragment(DataHome.EVENT4)
            findNavController().navigate(action)
        }
        imageEvent5.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailsEventFragment(DataHome.EVENT5)
            findNavController().navigate(action)
        }

    }

}
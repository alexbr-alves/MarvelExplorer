package alexProject.fragment

import alexProject.activity.MainActivity
import alexProject.databinding.FragmentHomeBinding
import alexProject.util.Constants
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
    override val viewModel: HomeViewModel by viewModels()
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = binding.run {
        contraintCharacters.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToCharacterFragment(
                    Constants.HOME,
                    0
                )
            findNavController().navigate(action)
        }
        contraintComics.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToComicsFragment(Constants.HOME, 0)
            findNavController().navigate(action)
        }
        contraintMoveis.setOnClickListener {
            mainActivity.goToMoviesActivity()
        }
        contraintTvshow.setOnClickListener {
            mainActivity.goToTvShow()
        }
        contraintGames.setOnClickListener {
            mainActivity.goToGamesActivity()
        }
        contraintTeams.setOnClickListener {
            mainActivity.goToGroupActivity()
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

}
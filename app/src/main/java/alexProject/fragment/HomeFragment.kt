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

    override val viewModel : HomeViewModel by viewModels()
    private val mainActivity : MainActivity by lazy { activity as MainActivity }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvents()
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    private fun clickEvents() = with(binding) {

        textTeams.setOnClickListener {
            mainActivity.goToGroupActivity()
        }

        textMovies.setOnClickListener {
            mainActivity.goToMoviesActivity()
        }

        textTvShow.setOnClickListener {
            mainActivity.goToTvShow()
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
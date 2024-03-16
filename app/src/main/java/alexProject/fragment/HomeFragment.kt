package alexProject.fragment

import alexProject.R
import alexProject.activity.MainActivity
import alexProject.adapters.MenuHomeAdapter
import alexProject.data.model.DestinationHome
import alexProject.data.model.MenuHomeModel
import alexProject.databinding.FragmentHomeBinding
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.viewModel.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    private val mainActivity: MainActivity by lazy { activity as MainActivity }
    private val menuAdapter by lazy { MenuHomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvents()
        collectObserve()
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            observe(list) {
                setupRecyclerView(it)
            }
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    private fun clickEvents() {
        menuAdapter.setOnItemClickListener {
            when (it.destination) {
                DestinationHome.COMIC -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToComicsFragment(Constants.HOME, 0)
                    findNavController().navigate(action)
                }

                DestinationHome.CHARACTERS -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToListCharacterFragment(
                            Constants.HOME,
                            0
                        )
                    findNavController().navigate(action)
                }

                DestinationHome.EVENTS -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToEventFragment2(Constants.HOME, 0)
                    findNavController().navigate(action)
                }

                DestinationHome.MOVIES -> mainActivity.goToMoviesActivity()
                DestinationHome.TV_SHOW -> mainActivity.goToTvShow()
                DestinationHome.GAMES -> mainActivity.goToGamesActivity()
                DestinationHome.TEAMS -> mainActivity.goToGroupActivity()
            }
        }
    }

    private fun setupRecyclerView(list: List<MenuHomeModel>) = with(binding) {
        recycleMenu.apply {
            adapter = menuAdapter
            menuAdapter.list = list
        }
    }

}
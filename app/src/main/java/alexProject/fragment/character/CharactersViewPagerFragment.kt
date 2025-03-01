package alexProject.fragment.character


import alexProject.activity.MainActivity
import alexProject.adapters.character.CharacterDetailsAdapter
import alexProject.databinding.FragmentCharacterViewPagerBinding
import alexProject.fragment.BaseFragment
import alexProject.fragment.DetailsFragmentDirections
import alexProject.util.Constants
import alexProject.util.observe
import alexProject.util.setGone
import alexProject.util.setVisible
import alexProject.viewModel.character.CharactersViewPagerViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersViewPagerFragment(private val comicId: Int, private val origin: String) :
    BaseFragment<FragmentCharacterViewPagerBinding, CharactersViewPagerViewModel>() {

    override val viewModel: CharactersViewPagerViewModel by viewModels()
    private val characterDetailsAdapter by lazy { CharacterDetailsAdapter() }
    private var totalItems: Int = 0
    private var initialOffset: Int = 0

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterViewPagerBinding =
        FragmentCharacterViewPagerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectObserver()
        clickAdapter()
    }

    private fun clickAdapter() {
        characterDetailsAdapter.setOnClickListener { characterModel ->
            val action = DetailsFragmentDirections.actionDetailsFragmentSelf(
                comic = Constants.COMIC_INVALID,
                character = characterModel,
                event = Constants.EVENT_INVALID
            )
            findNavController().navigate(action)
        }
        binding.textSeeMore.setOnClickListener {
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToCharacterFragment(
                    Constants.COMIC,
                    comicId
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = characterDetailsAdapter
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= INITIAL_POSITION && totalItems > initialOffset + 30) {
                        textSeeMore.setVisible()
                    } else {
                        textSeeMore.setGone()
                    }
                }
            })
        }
    }

    private fun collectObserver() = with(viewModel) {
        getCharacter(comicId, origin)
        observe(onError) { onError ->
            (activity as MainActivity).showError(onError.message)
        }
        observe(onLoading) { isLoading ->
            binding.progressBarDetail.setVisible(isLoading)
        }
        observe(comicCharacterList) { result ->
            result?.apiResponse?.let { characterModels ->
                if (characterModels.entityList.isEmpty()) {
                    binding.textEmpty.setVisible()
                } else {
                    characterDetailsAdapter.characters = characterModels.entityList.toList()
                    totalItems = characterModels.total
                }
            }
        }
    }

    companion object {
        const val SPAN_COUNT = 3
        const val MAX_SIZE = 30
        const val INITIAL_POSITION = 0
    }
}

package alexProject.fragment

import alexProject.activity.MoviesActivity
import alexProject.adapters.StoryViewPagerAdapter
import alexProject.data.model.StoryModel
import alexProject.databinding.FragmentStoryBinding
import alexProject.util.observe
import alexProject.viewModel.StoryViewModel
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding, StoryViewModel>() {
    override val viewModel: StoryViewModel by viewModels()
    private val args: StoryFragmentArgs by navArgs()
    private var viewPager: ViewPager2? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoryBinding = FragmentStoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        collectObserve()
    }

    private fun setupUI() {
        binding.imageClose.setOnClickListener {
            activity?.onBackPressed()
        }
        handleViewPager()
    }

    private fun handleViewPager() {
        viewPager = binding.viewPagerStory
        val storyViewPagerAdapter = StoryViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
            storyModel = args.storyModel
        )
        viewPager?.adapter = storyViewPagerAdapter
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager?.offscreenPageLimit = 1
            }
        })
        viewPager?.post {
            viewPager?.setCurrentItem(getIndexOfStory(args.storyModel.data, args.id), false)
        }
    }

    fun getIndexOfStory(storyModels: List<StoryModel>, idProcurado: Int): Int {
        for ((index, story) in storyModels.withIndex()) {
            if (story.id == idProcurado) {
                return index
            }
        }
        return -1
    }

    private fun collectObserve() = lifecycleScope.launch {
        with(viewModel) {
            observe(onError) { onError ->
                (activity as MoviesActivity).showError(onError)
            }
        }
    }

    fun onNextPage() {
        val viewPagerStory =  binding.viewPagerStory
        viewPagerStory.setCurrentItem(viewPagerStory.currentItem + 1, true)
    }

    fun onPreviousPage() {
        val viewPagerStory =  binding.viewPagerStory
        viewPagerStory.setCurrentItem(viewPagerStory.currentItem - 1, true)
    }
}
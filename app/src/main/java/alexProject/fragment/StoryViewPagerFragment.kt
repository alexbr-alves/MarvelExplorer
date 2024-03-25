package alexProject.fragment

import alexProject.data.model.StoryModel
import alexProject.databinding.FragmentStoryViewPagerBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import alexProject.util.setGone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class StoryViewPagerFragment(
    private val storyModel: StoryModel,
    private val id: Int,
    private val lastPosition: Boolean
) : Fragment() {
    private var binding: FragmentStoryViewPagerBinding? = null
    private var currentComicIndex = MIN_INDEX

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryViewPagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = binding?.run {
        loadStory()
        clickEvent()
    }

    private fun clickEvent() = binding?.run {
        image.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val rightSide = event.x > v.width / DIVISION_WIDTH
                    if (rightSide) {
                        onRightSide()
                    } else {
                        onLeftSide()
                    }
                }
            }
            true
        }
    }

    private fun onLeftSide() {
        if (currentComicIndex > MIN_INDEX) {
            currentComicIndex--
        } else {
            (parentFragment as StoryFragment).onPreviousPage()
        }
        loadStory()
    }

    private fun onRightSide() {
        if (currentComicIndex < MAX_INDEX) {
            currentComicIndex++
        } else {
            if (lastPosition) {
                activity?.onBackPressed()
            } else {
                (parentFragment as StoryFragment).onNextPage()
            }
        }
        loadStory()
    }

    private fun loadStory() = binding?.run {
        val story = storyModel.comics[currentComicIndex]
        loadImage(
            image,
            story.thumbnailModel.path,
            story.thumbnailModel.extension
        )

        textTitle.text = removeParenthesesOnTitle(story.title)
        if (!story.description.isNullOrEmpty()) {
            textDescription.text = story.description.limitDescription(DESCRIPTION_MAX_LENGTH)
        } else {
            textDescription.setGone()
        }
        setupProgress()
    }

    private fun setupProgress() = binding?.run {
        val progresses = listOf(PROGRESS_COMPLETED, PROGRESS_COMPLETED, PROGRESS_COMPLETED)
        listOf(progressBar1, progressBar2, progressBar3).forEachIndexed { index, progressBar ->
            progressBar.progress = if (index <= currentComicIndex) progresses[index] else PROGRESS_INITIAL
        }
    }

    companion object {
        const val MAX_INDEX = 2
        const val MIN_INDEX = 0
        const val PROGRESS_COMPLETED = 100
        const val PROGRESS_INITIAL = 0
        const val DESCRIPTION_MAX_LENGTH = 300
        const val DIVISION_WIDTH = 2
    }
}
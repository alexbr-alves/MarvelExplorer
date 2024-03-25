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
    private var currentComicIndex = 0

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
                    val rightSide = event.x > v.width / 2
                    if (rightSide) {
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
                    } else {
                        if (currentComicIndex > 0) {
                            currentComicIndex--
                        } else {
                            (parentFragment as StoryFragment).onPreviousPage()
                        }
                        loadStory()
                    }
                }
            }
            true
        }
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
            textDescription.text = story.description.limitDescription(200).lowercase()
        } else {
            textDescription.setGone()
        }
        when (currentComicIndex) {
            0 -> {
                progressBar1.progress = 100
                progressBar2.progress = 0
                progressBar3.progress = 0
            }

            1 -> {
                progressBar1.progress = 100
                progressBar2.progress = 100
                progressBar3.progress = 0
            }

            2 -> {
                progressBar1.progress = 100
                progressBar2.progress = 100
                progressBar3.progress = 100
            }
        }
    }

    companion object {
        const val MAX_INDEX = 2
        const val DELAY_MILLIS = 15000L
    }
}
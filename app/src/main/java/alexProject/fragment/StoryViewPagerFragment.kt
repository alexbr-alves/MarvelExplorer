package alexProject.fragment

import alexProject.data.model.StoryModel
import alexProject.data.model.StoryModelData
import alexProject.databinding.FragmentStoryViewPagerBinding
import alexProject.util.limitDescription
import alexProject.util.loadImage
import alexProject.util.removeParenthesesOnTitle
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class StoryViewPagerFragment(
    private val storyModel: StoryModel,
    private val id: Int
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
        image.setOnClickListener {
            if (currentComicIndex < MAX_INDEX) {
                currentComicIndex++
                loadStory()
            }else {
                (parentFragment as StoryFragment).onNextPage()
            }

        }
    }

    private fun loadStory() = binding?.run {
        val storyModel = storyModel.comics[currentComicIndex]
        loadImage(
            image,
            storyModel.thumbnailModel.path,
            storyModel.thumbnailModel.extension
        )
        textTitle.text = removeParenthesesOnTitle(storyModel.title)
        if (!storyModel.description.isNullOrEmpty()) {
            textDescription.text = storyModel.description.limitDescription(200)
        }

        when (currentComicIndex) {
            0 -> progressBarComics.progress = 33
            1 -> progressBarComics.progress = 66
            2 -> progressBarComics.progress = 100
        }
    }


    companion object {
        const val MAX_INDEX = 2
    }
}
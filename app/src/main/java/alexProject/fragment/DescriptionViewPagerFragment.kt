package alexProject.fragment

import alexProject.databinding.FragmentDescriptionViewPagerBinding
import alexProject.util.removeParenthesesOnTitle
import alexProject.util.setGone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DescriptionViewPagerFragment(private val title : String, private val description : String) : Fragment() {
    private var _binding : FragmentDescriptionViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentDescriptionViewPagerBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() = binding.run {
        textTitle.text = removeParenthesesOnTitle(title)
        if (description.isEmpty()) {
            textDescription.setGone()
        } else {
            textDescription.text = description
        }
    }
}
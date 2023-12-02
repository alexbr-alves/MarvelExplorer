package alexProject.fragment.detailsComic

import alexProject.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alexProject.databinding.FragmentDescriptionComicBinding

class ComicDescriptionFragment(private val description : String) : Fragment() {
    private var _binding : FragmentDescriptionComicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentDescriptionComicBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() = binding.run {
        if (description.isEmpty()) {
            textDescription.text = getString(R.string.common_no_information)
        } else {
            textDescription.text = description
        }
    }

}
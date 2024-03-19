package alexProject.fragment


import alexProject.R
import alexProject.databinding.FragmentDescriptionCharacterBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DescriptionViewPagerFragment(private val description : String) : Fragment() {
    private var _binding : FragmentDescriptionCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentDescriptionCharacterBinding.inflate(inflater, container, false)
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
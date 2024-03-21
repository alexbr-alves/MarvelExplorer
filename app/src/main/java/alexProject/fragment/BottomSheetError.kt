import alexProject.databinding.BottomSheetErrorBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetError(
    private val message: String,
    private val onRetryClick: () -> Unit
) : BottomSheetDialogFragment() {
    private var binding: BottomSheetErrorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetErrorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = binding?.run {
        errorMessage.text = message

        buttonRetry.setOnClickListener {
            onRetryClick()
            dismiss()
        }
    }

    companion object {
        const val TAG = "BottomSheetError"
    }
}

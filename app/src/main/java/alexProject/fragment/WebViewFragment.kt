package alexProject.fragment

import alexProject.databinding.FragmentWebviewBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebviewBinding
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        webView.loadUrl(args.url)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
    }

}
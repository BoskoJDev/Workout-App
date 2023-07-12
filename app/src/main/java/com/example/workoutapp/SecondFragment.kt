package com.example.workoutapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.MediaController
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.videoView.webViewClient = WebViewClient()
        binding.videoView.webChromeClient = WebChromeClient()
        binding.videoView.settings.javaScriptEnabled = true

        val html = "<html><body><br><iframe width=\"320\" height=\"315\"" +
                "src=\"" + requireActivity().intent?.getStringExtra("videoLink")!! + "\" frameborder=\"0\"" +
                "allowfullscreen></iframe></body></html>"
        binding.videoView.loadData(html, "text/html", "utf-8")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
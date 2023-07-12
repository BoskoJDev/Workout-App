package com.example.workoutapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var videoURLList: Array<out String>
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        this.videoURLList = resources.getStringArray(R.array.workoutVideoURLs)

        binding.absBtn.setOnClickListener(this.setVideoOnClickListener(0))
        binding.backBtn.setOnClickListener(this.setVideoOnClickListener(1))
        binding.bigArmsBtn.setOnClickListener(this.setVideoOnClickListener(2))
        binding.wideShouldersBtn.setOnClickListener(this.setVideoOnClickListener(3))
        binding.tricepsBtn.setOnClickListener(this.setVideoOnClickListener(4))
        binding.chestBtn.setOnClickListener(this.setVideoOnClickListener(5))
        binding.warmUpBtn.setOnClickListener(this.setVideoOnClickListener(6))
        binding.legsBtn.setOnClickListener(this.setVideoOnClickListener(7))
        binding.postureBtn.setOnClickListener(this.setVideoOnClickListener(8))
    }

    private fun setVideoOnClickListener(videoIndex: Int): View.OnClickListener
    {
        return View.OnClickListener {
            this.requireActivity().intent?.putExtra("videoLink", this.videoURLList[videoIndex])
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.toyproject.bookandmoviereview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.toyproject.bookandmoviereview.databinding.FragmentDiscussionBoardMovieBinding

class DiscussionBoardMovieFragment : Fragment() {
    private var _binding: FragmentDiscussionBoardMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscussionBoardMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
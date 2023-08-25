package com.toyproject.bookandmoviereview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.toyproject.bookandmoviereview.databinding.FragmentDiscussionBoardBinding

class DiscussionBoardFragment : Fragment() {
    private var _binding: FragmentDiscussionBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscussionBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = binding.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("책"))
        tabLayout.addTab(tabLayout.newTab().setText("영화"))

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> replaceFragment(DiscussionBoardBookFragment())
                    1 -> replaceFragment(DiscussionBoardMovieFragment())
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 기본 화면을 DiscussionBoardBookFragment()로 설정
        if (savedInstanceState == null) {
            replaceFragment(DiscussionBoardBookFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
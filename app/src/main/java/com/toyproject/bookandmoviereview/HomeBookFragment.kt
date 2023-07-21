package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.databinding.FragmentHomeBookBinding

class HomeBookFragment : Fragment() {
    private var _binding: FragmentHomeBookBinding? = null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null
    private var recyclerViewBookAdapter: RecyclerViewBookAdapter? = null
    private var bookList = mutableListOf<BookData>()

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareBookListData() {
        var book = BookData("부의 인문학", "우석", 4.5F, 273, "https://image.aladin.co.kr/product/28967/26/cover500/k032836725_1.jpg")
        bookList.add(book)

        book = BookData("세이노의 가르침", "세이노", 5.0F, 34, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        bookList.add(book)

        book = BookData("세이노의 가르침", "세이노", 3.5F, 12, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        bookList.add(book)

        book = BookData("세이노의 가르침", "세이노", 3.0F, 5, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        bookList.add(book)

        book = BookData("세이노의 가르침", "세이노", 3.5F, 12, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        bookList.add(book)

        book = BookData("세이노의 가르침", "세이노", 3.0F, 5, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        bookList.add(book)

        recyclerViewBookAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundResource(R.color.light_mode_home_background)

        bookList = ArrayList()
        recyclerView = binding.recyclerGridView

        recyclerViewBookAdapter = RecyclerViewBookAdapter(this@HomeBookFragment, bookList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewBookAdapter

        prepareBookListData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
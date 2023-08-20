package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.databinding.FragmentHomeBookBinding
import java.util.*

data class ComparableScore(var bookData: BookData) : Comparable<ComparableScore> {
    override fun compareTo(other: ComparableScore): Int {
        return ((other.bookData.score - bookData.score) * 10).toInt()
    }
}

data class ComparableReviews(var bookData: BookData) : Comparable<ComparableReviews> {
    override fun compareTo(other: ComparableReviews): Int {
        return other.bookData.numberOfReviews - bookData.numberOfReviews
    }
}

class HomeBookFragment : Fragment() {
    private var _binding: FragmentHomeBookBinding? = null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null
    private var recyclerViewBookAdapter: RecyclerViewBookAdapter? = null
    private var bookList = mutableListOf<BookData>()

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareBookListData() {
        val pq = PriorityQueue<ComparableScore>()

        var book = BookData("부의 인문학", "우석", 4.3F, 273, 8, "https://image.aladin.co.kr/product/28967/26/cover500/k032836725_1.jpg")
        pq.add(ComparableScore(book))

        book = BookData("세이노의 가르침", "세이노", 5.0F, 34, 7, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        pq.add(ComparableScore(book))

        book = BookData("세이노의 가르침", "세이노", 3.7F, 12, 6, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        pq.add(ComparableScore(book))

        book = BookData("세이노의 가르침", "세이노", 3.0F, 5, 5, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        pq.add(ComparableScore(book))

        book = BookData("세이노의 가르침", "세이노", 3.5F, 12, 4, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        pq.add(ComparableScore(book))

        book = BookData("세이노의 가르침", "세이노", 3.2F, 5, 0, "https://image.aladin.co.kr/product/30929/51/cover500/s302832892_1.jpg")
        pq.add(ComparableScore(book))

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookList.add(it.bookData) }
        }

        recyclerViewBookAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortBookListDataByHighestScore() {
        val pq = PriorityQueue<ComparableScore>()

        for (bookData in bookList) {
            pq.add(ComparableScore(bookData))
        }

        bookList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookList.add(it.bookData) }
        }

        recyclerViewBookAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortBookListDataByMostReviews() {
        val pq = PriorityQueue<ComparableReviews>()

        for (bookData in bookList) {
            pq.add(ComparableReviews(bookData))
        }

        bookList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookList.add(it.bookData) }
        }

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
        binding.textHighestScore.setTypeface(null, Typeface.BOLD)
        binding.textMostReviews.setTypeface(null, Typeface.NORMAL)

        // 최고 평점 버튼 클릭 시
        binding.buttonHighestScore.setOnClickListener {
            sortBookListDataByHighestScore()
            binding.textHighestScore.setTypeface(null, Typeface.BOLD)
            binding.textMostReviews.setTypeface(null, Typeface.NORMAL)
        }

        // 리뷰 많은 순 버튼 클릭 시
        binding.buttonMostReviews.setOnClickListener {
            sortBookListDataByMostReviews()
            binding.textHighestScore.setTypeface(null, Typeface.NORMAL)
            binding.textMostReviews.setTypeface(null, Typeface.BOLD)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
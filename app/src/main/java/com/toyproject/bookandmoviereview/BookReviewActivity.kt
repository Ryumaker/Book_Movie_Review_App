package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.databinding.ActivityBookReviewBinding
import com.toyproject.bookandmoviereview.models.ReplyData
import java.time.LocalDate
import java.util.PriorityQueue

private data class ComparableUploadDateForReplyData(var replyData: ReplyData) : Comparable<ComparableUploadDateForReplyData> {
    override fun compareTo(other: ComparableUploadDateForReplyData): Int {
        return if (other.replyData.uploadDate > replyData.uploadDate) {
            1
        } else if (other.replyData.uploadDate < replyData.uploadDate) {
            -1
        } else {
            0
        }
    }
}

class BookReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewBinding
    private var recyclerView: RecyclerView? = null
    private var recyclerViewReplyAdapter: RecyclerViewReplyAdapter? = null
    private var replyList = mutableListOf<ReplyData>()

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareReplyListData() {
        val pq = PriorityQueue<ComparableUploadDateForReplyData>()

        var comment = ReplyData("Helene Moore", LocalDate.of(2019, 12, 1), "Sed in lorem ac lacus commodo commodo.")
        pq.add(ComparableUploadDateForReplyData(comment))

        comment = ReplyData("Mauris urna ligula", LocalDate.of(2019, 11, 15), "Nullam id ante bibendum, convallis tortor sit amet, vestibulum augue.")
        pq.add(ComparableUploadDateForReplyData(comment))

        comment = ReplyData("tempus et", LocalDate.of(2019, 10, 22), "Donec eget ante sed metus tincidunt eleifend.")
        pq.add(ComparableUploadDateForReplyData(comment))

        comment = ReplyData("Nullam nec", LocalDate.of(2019, 10, 23), "Pellentesque accumsan felis sit amet mattis ullamcorper. Suspendisse semper urna sit amet nibh tempor, et congue lacus vestibulum.")
        pq.add(ComparableUploadDateForReplyData(comment))

        comment = ReplyData("Sem tristique metus", LocalDate.of(2019, 9, 5), "Morbi ut bibendum nisi. Maecenas ac dui id nibh malesuada lobortis a vel elit. Praesent consequat dolor nisl, a sagittis risus feugiat ac. Sed facilisis sollicitudin dictum. Fusce eu tortor sit amet mauris bibendum varius. Pellentesque tempor elementum ultrices. Aenean semper tortor id sapien commodo faucibus. Quisque malesuada massa vel tempus sodales.")
        pq.add(ComparableUploadDateForReplyData(comment))

        while (pq.isNotEmpty()) {
            pq.poll()?.let { replyList.add(it.replyData) }
        }

        recyclerViewReplyAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val nickname = intent.getStringExtra("nickname")
        val uploadDate = intent.getStringExtra("uploadDate")
        val review = intent.getStringExtra("review")
        val numberOfThumbUp = intent.getStringExtra("numberOfThumbUp")
        val score = intent.getFloatExtra("score", 0.0F)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 앱바에 back 버튼 활성화

        binding.reviewListGridItem.textNickname.text = nickname
        binding.reviewListGridItem.textUploadDate.text = uploadDate
        binding.reviewListGridItem.textReview.text = review
        binding.reviewListGridItem.textNumberOfThumbUp.text = numberOfThumbUp
        binding.reviewListGridItem.ratingBar.rating = score

        recyclerView = binding.recyclerViewForReply
        recyclerViewReplyAdapter = RecyclerViewReplyAdapter(this, replyList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewReplyAdapter
        prepareReplyListData()

        binding.textNumberOfReplies.text = "${replyList.size} Replies"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        if (menu != null) {
            for (i in 0 until menu.size()) {
                if (menu.getItem(i).itemId == R.id.action_search) {
                    menu.getItem(i).isVisible = false // 검색 아이콘을 숨김
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish() // back 버튼을 누르면 이전 화면으로 돌아감
        }
        return true
    }
}
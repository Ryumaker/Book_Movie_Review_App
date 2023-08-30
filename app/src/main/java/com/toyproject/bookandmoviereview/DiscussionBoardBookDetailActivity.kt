package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.databinding.ActivityDiscussionBoardBookDetailBinding
import com.toyproject.bookandmoviereview.models.ReplyData
import java.time.LocalDate
import java.util.PriorityQueue

class DiscussionBoardBookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscussionBoardBookDetailBinding
    private var recyclerView: RecyclerView? = null
    private var recyclerViewReplyAdapter: RecyclerViewReplyAdapter? = null
    private var commentList = mutableListOf<ReplyData>()

    private data class ComparableUploadDateForCommentData(var replyData: ReplyData) : Comparable<ComparableUploadDateForCommentData> {
        override fun compareTo(other: ComparableUploadDateForCommentData): Int {
            return if (other.replyData.uploadDate > replyData.uploadDate) {
                1
            } else if (other.replyData.uploadDate < replyData.uploadDate) {
                -1
            } else {
                0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareCommentListData() {
        val pq = PriorityQueue<ComparableUploadDateForCommentData>()

        var comment = ReplyData(
            "Suspendisse vitae lacus",
            LocalDate.of(2023, 7, 15),
            "Cras et lectus efficitur, tincidunt nisl id, aliquet erat."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        comment = ReplyData(
            "Mauris nec",
            LocalDate.of(2023, 7, 14),
            "Donec aliquet neque a lobortis accumsan."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        comment = ReplyData(
            "Pretium orci varius",
            LocalDate.of(2023, 7, 13),
            "Quisque tempor ligula eget pretium tincidunt. Curabitur luctus lacus eu ex sagittis euismod. Aenean luctus quam sed dui fringilla, nec lobortis tellus tempor."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        comment = ReplyData(
            "Morbi quis urna at justo",
            LocalDate.of(2023, 7, 12),
            "Proin nec augue ut purus facilisis volutpat eu in justo. Quisque faucibus velit ut tincidunt auctor."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        comment = ReplyData(
            "Venenatis pellentesque",
            LocalDate.of(2023, 7, 11),
            "Proin luctus eu mauris vitae interdum. Duis luctus urna a nisi mollis, in consequat augue euismod. Aliquam risus ante, pulvinar a pellentesque id, lobortis at justo. In blandit in elit ultrices consectetur. Vivamus at sollicitudin enim, a bibendum neque. Vestibulum et libero elit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer pulvinar leo augue, sit amet ullamcorper justo tincidunt eget. Maecenas et tincidunt ex. Curabitur at tristique nulla, cursus mollis risus. Phasellus sit amet maximus nulla, eget feugiat odio. Curabitur semper lectus efficitur, congue libero vel, pellentesque dolor. Fusce pellentesque, nisi nec venenatis ultricies, nunc purus vestibulum lacus, eget tristique elit risus sed nisl. Sed aliquet vitae mi eu rutrum. Integer sit amet lacus posuere, condimentum dui eget, fermentum elit."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        comment = ReplyData(
            "Curabitur vel felis porttitor",
            LocalDate.of(2023, 7, 10),
            "Praesent id erat et erat euismod faucibus."
        )
        pq.add(ComparableUploadDateForCommentData(comment))

        while (pq.isNotEmpty()) {
            pq.poll()?.let { commentList.add(it.replyData) }
        }

        recyclerViewReplyAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscussionBoardBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookTitle = intent.getStringExtra("bookTitle")
        val nickname = intent.getStringExtra("nickname")
        val discussionTitle = intent.getStringExtra("discussionTitle")

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = bookTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 앱바에 back 버튼 활성화

        binding.textDiscussionTitle.text = discussionTitle
        binding.textDiscussion.text = "Aenean nunc erat, eleifend at sem vel, placerat blandit massa. Phasellus cursus, arcu at aliquam vehicula, elit neque imperdiet nisi, eget hendrerit risus urna vel lorem. Morbi quam odio, pharetra sit amet pharetra eget, tempus eu metus. Fusce viverra mollis diam, eget sodales ligula interdum vel. Curabitur consectetur vel sem accumsan interdum. In iaculis eleifend arcu. Pellentesque ullamcorper euismod sollicitudin. Aliquam erat volutpat. Mauris turpis velit, ornare sed ante eu, euismod pellentesque dolor. Aliquam hendrerit laoreet ex et congue. Morbi a est consequat, rhoncus tellus non, vehicula est. In hac habitasse platea dictumst. Phasellus ut enim porta, efficitur urna iaculis, mattis felis. Aenean ornare venenatis arcu quis ultricies. Aenean urna risus, tincidunt in est eget, tristique tincidunt elit. Pellentesque ut volutpat diam, non tincidunt nisi."

        recyclerView = binding.recyclerViewForDiscussionBookComments
        recyclerViewReplyAdapter = RecyclerViewReplyAdapter(this, commentList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewReplyAdapter
        prepareCommentListData()

        binding.textNumberOfComments.text = "${commentList.size} Comments"
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
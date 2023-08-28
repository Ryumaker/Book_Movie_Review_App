package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.toyproject.bookandmoviereview.databinding.ActivityDiscussionBoardBookDetailBinding

class DiscussionBoardBookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiscussionBoardBookDetailBinding

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
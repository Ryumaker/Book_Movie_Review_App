package com.toyproject.bookandmoviereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.toyproject.bookandmoviereview.databinding.ActivityBookReviewBinding

class BookReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewBinding

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
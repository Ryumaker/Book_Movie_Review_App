package com.toyproject.bookandmoviereview

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.toyproject.bookandmoviereview.databinding.ActivityMainBinding
import com.toyproject.bookandmoviereview.models.WISE_SATING_LIST_BOOK

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gestureDetector: GestureDetector

    private var x1 = 0.0f
    private var x2 = 0.0f

    companion object {
        const val MIN_DISTANCE = 150
        const val LEFT_PADDING = 50
        const val RIGHT_PADDING = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestureDetector = GestureDetector(this, this)

        binding.textWiseSayingEng.text = WISE_SATING_LIST_BOOK[0][0]
        binding.textWiseSayingEng.setPadding(LEFT_PADDING, 0, RIGHT_PADDING, 0)
        binding.textWiseSayingKor.text = WISE_SATING_LIST_BOOK[0][1]
        binding.textWiseSayingKor.setPadding(LEFT_PADDING, 0, RIGHT_PADDING, 0)
        binding.textWiseSayingAuthor.text = WISE_SATING_LIST_BOOK[0][2]
        binding.textWiseSayingAuthor.setPadding(LEFT_PADDING, 0, RIGHT_PADDING, 0)

        // 텍스트에 에니메이션 효과 추가
        val animation: Animation
        animation = AlphaAnimation(0.2f, 0.8f)
        animation.setDuration(1000)
        animation.setStartOffset(20)
        animation.setRepeatMode(Animation.REVERSE)
        animation.setRepeatCount(Animation.INFINITE)

        binding.textSwipeToLeft.startAnimation(animation)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            gestureDetector.onTouchEvent(event)
        }

        when (event?.action) {
            // swipe start
            0 -> {
                x1 = event.x
            }

            // swipe end
            1 -> {
                x2 = event.x

                val valueX = x2 - x1

                // 화면 왼쪽으로 슬라이드하면 로그인 화면으로 넘어가도록 구현
                if (kotlin.math.abs(valueX) > MIN_DISTANCE) {
                    if (x1 > x2) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {}

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {}

    override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }
}
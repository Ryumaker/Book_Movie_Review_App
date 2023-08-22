package com.toyproject.bookandmoviereview

import android.R.attr.value
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.toyproject.bookandmoviereview.databinding.ActivityBookReviewListBinding
import com.toyproject.bookandmoviereview.models.BookReviewData
import java.time.LocalDate
import java.util.*

class XAxisValueFormatter : ValueFormatter() {
    private var index = 0

    override fun getFormattedValue(value: Float): String {
        index = value.toInt()
        return when (index) {
            0 -> "★"
            1 -> "★★"
            2 -> "★★★"
            3 -> "★★★★"
            4 -> "★★★★★"
            else -> throw IndexOutOfBoundsException("index out")
        }
    }
}

data class ComparableThumpUp(var bookReviewData: BookReviewData) : Comparable<ComparableThumpUp> {
    override fun compareTo(other: ComparableThumpUp): Int {
        return other.bookReviewData.numberOfThumbUp - bookReviewData.numberOfThumbUp
    }
}

data class ComparableUploadDate(var bookReviewData: BookReviewData) : Comparable<ComparableUploadDate> {
    override fun compareTo(other: ComparableUploadDate): Int {
        return if (other.bookReviewData.uploadDate > bookReviewData.uploadDate) {
            1
        } else if (other.bookReviewData.uploadDate < bookReviewData.uploadDate) {
            -1
        } else {
            0
        }
    }
}

class BookReviewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewListBinding
    private lateinit var chart : HorizontalBarChart

    private var recyclerView: RecyclerView? = null
    private var recyclerViewBookReviewAdapter: RecyclerViewBookReviewAdapter? = null
    private var bookReviewList = mutableListOf<BookReviewData>()

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareBookReviewListData() {
        val pq = PriorityQueue<ComparableThumpUp>()

        var review = BookReviewData("Helene Moore", 4.0F, LocalDate.of(2019, 7, 5), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 5)
        pq.add(ComparableThumpUp(review))

        review = BookReviewData("Kate Doe", 4.5F, LocalDate.of(2019, 7, 6), "Nullam sit amet facilisis tortor.", 1)
        pq.add(ComparableThumpUp(review))

        review = BookReviewData("Helene Moore", 4.0F, LocalDate.of(2019, 7, 7), "Duis tempus libero nec imperdiet blandit. Morbi dictum mollis mauris, vel cursus augue condimentum a.", 4)
        pq.add(ComparableThumpUp(review))

        review = BookReviewData("Helene Moore", 4.0F, LocalDate.of(2019, 7, 8), "Cras interdum eget dolor sed sollicitudin. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 2)
        pq.add(ComparableThumpUp(review))

        review = BookReviewData("Kate Doe", 4.5F, LocalDate.of(2019, 7, 9), "Aenean nunc nisl, eleifend eu sodales ac, mattis id sapien.", 3)
        pq.add(ComparableThumpUp(review))

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookReviewList.add(it.bookReviewData) }
        }

        recyclerViewBookReviewAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortBookReviewByHighestThumpUp() {
        val pq = PriorityQueue<ComparableThumpUp>()

        for (bookReview in bookReviewList) {
            pq.add(ComparableThumpUp(bookReview))
        }

        bookReviewList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookReviewList.add(it.bookReviewData) }
        }

        recyclerViewBookReviewAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortBookReviewByMostRecentUploadDate() {
        val pq = PriorityQueue<ComparableUploadDate>()

        for (bookReview in bookReviewList) {
            pq.add(ComparableUploadDate(bookReview))
        }

        bookReviewList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { bookReviewList.add(it.bookReviewData) }
        }

        recyclerViewBookReviewAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val score = intent.getStringExtra("score")
        val numberOfRatings = intent.getStringExtra("numberOfRatings")
        val numberOfReviews = intent.getStringExtra("numberOfReviews")

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 앱바에 back 버튼 활성화

        Glide.with(this).load(imageUrl).into(binding.imageBook)
        binding.textRatingsAndReviews.text = "Ratings & Reviews (${numberOfRatings})"

        binding.textScore.text = score
        binding.textNumberOfRatings.text = "$numberOfRatings Ratings"

        binding.textNumberOfReviews.text = "$numberOfReviews Reviews"

        val items = resources.getStringArray(R.array.sort_method)
        val sortMethodSpinner = binding.sortMethodSpinner
        val sortMethodAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        sortMethodSpinner.adapter = sortMethodAdapter
        sortMethodSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> { // 최다 공감순
                        sortBookReviewByHighestThumpUp()
                    }
                    1 -> { // 최신순
                        sortBookReviewByMostRecentUploadDate()
                    }
                    else -> { // 최다 공감순
                        sortBookReviewByHighestThumpUp()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        sortMethodSpinner.setSelection(0) // 0번째 아이템(최다 공감순)이 기본적으로 선택되도록 설정

        binding.btnWritingReview.setOnClickListener {
            val intent = Intent(this, BookReviewUploadActivity::class.java)
            intent.putExtra("title", title)
            startActivity(intent)
        }

        if (numberOfRatings != null) {
            setSkillGraph(numberOfRatings.toFloat())
            val ratings =  ArrayList<Float>()
            ratings.add(27f) // ★
            ratings.add(45f) // ★★
            ratings.add(65f) // ★★★
            ratings.add(77f) // ★★★★
            ratings.add(93f) // ★★★★★
            setGraphData(ratings)
        }

        recyclerView = binding.recyclerGridView

        recyclerViewBookReviewAdapter = RecyclerViewBookReviewAdapter(this, bookReviewList, title)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewBookReviewAdapter
        prepareBookReviewListData()
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

    private fun setGraphData(ratings: ArrayList<Float>) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, ratings[0])) // ★
        entries.add(BarEntry(1f, ratings[1])) // ★★
        entries.add(BarEntry(2f, ratings[2])) // ★★★
        entries.add(BarEntry(3f, ratings[3])) // ★★★★
        entries.add(BarEntry(4f, ratings[4])) // ★★★★★

        val barDataSet = BarDataSet(entries, "Bar Data Set")
        barDataSet.setColors(
            ContextCompat.getColor(chart.context, R.color.horizontal_chart),
            ContextCompat.getColor(chart.context, R.color.horizontal_chart),
            ContextCompat.getColor(chart.context, R.color.horizontal_chart),
            ContextCompat.getColor(chart.context, R.color.horizontal_chart),
            ContextCompat.getColor(chart.context, R.color.horizontal_chart)
        )

        chart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        barDataSet.valueFormatter = DefaultValueFormatter(0) // 그래프 데이터를 정수형으로 변환
        barDataSet.valueTextSize = 10f
        barDataSet.setDrawValues(true)
        val data = BarData(barDataSet)
        data.barWidth = 0.9f
        chart.data = data
        chart.invalidate()
    }

    private fun setSkillGraph(reviews: Float) {
        chart = binding.chart
        chart.setDrawBarShadow(false)
        val description = Description()
        description.isEnabled = false
        chart.description = description
        chart.legend.isEnabled = false
        chart.setPinchZoom(false)
        chart.setScaleEnabled(false)
        chart.setDrawValueAboveBar(false)
        chart.setTouchEnabled(false)
        chart.animateY(1000)
        chart.setDrawValueAboveBar(false)

        val xAxis = chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.isEnabled = true
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = 1f

        val yLeft = chart.axisLeft
        yLeft.axisMaximum = reviews
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        xAxis.labelCount = 5
        xAxis.valueFormatter = XAxisValueFormatter()
        xAxis.textSize = 12f

        val yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false
        yRight.axisMaximum = 0f
        yRight.axisMinimum = 0f
    }
}
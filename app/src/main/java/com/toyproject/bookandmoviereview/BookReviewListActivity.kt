package com.toyproject.bookandmoviereview

import android.R.attr.value
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

class BookReviewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewListBinding
    private lateinit var chart : HorizontalBarChart

    private var recyclerView: RecyclerView? = null
    private var recyclerViewBookReviewAdapter: RecyclerViewBookReviewAdapter? = null
    private var bookReviewList = mutableListOf<BookReviewData>()

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareBookReviewListData() {
        var review = BookReviewData("Helene Moore", 4.0F, "2019.7.5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 10)
        bookReviewList.add(review)

        review = BookReviewData("Kate Doe", 4.5F, "2019.7.6", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 5)
        bookReviewList.add(review)

        review = BookReviewData("Helene Moore", 4.0F, "2019.7.5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 10)
        bookReviewList.add(review)

        review = BookReviewData("Helene Moore", 4.0F, "2019.7.5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 10)
        bookReviewList.add(review)

        review = BookReviewData("Kate Doe", 4.5F, "2019.7.6", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 5)
        bookReviewList.add(review)

        recyclerViewBookReviewAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val rating = intent.getStringExtra("rating")
        val numberOfComments = intent.getStringExtra("numberOfComments")

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 앱바에 back 버튼 활성화


        Glide.with(this).load(imageUrl).into(binding.imageBook)
        binding.textRatingsAndReviews.text = "Ratings & Reviews (${numberOfComments})"

        binding.textRating.text = rating
        binding.textNumberOfReviews.text = "$numberOfComments Reviews"

        if (numberOfComments != null) {
            setSkillGraph(numberOfComments.toFloat())
            val ratings =  ArrayList<Float>()
            ratings.add(27f) // ★
            ratings.add(45f) // ★★
            ratings.add(65f) // ★★★
            ratings.add(77f) // ★★★★
            ratings.add(93f) // ★★★★★
            setGraphData(ratings)
        }

        recyclerView = binding.recyclerGridView

        recyclerViewBookReviewAdapter = RecyclerViewBookReviewAdapter(this, bookReviewList)
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
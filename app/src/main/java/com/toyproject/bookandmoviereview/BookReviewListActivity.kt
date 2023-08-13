package com.toyproject.bookandmoviereview

import android.R.attr.value
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.MarkerView
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
            0 -> "1★"
            1 -> "2★"
            2 -> "3★"
            3 -> "4★"
            4 -> "5★"
            else -> throw IndexOutOfBoundsException("index out")
        }
    }
}

class BookReviewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReviewListBinding
    private lateinit var chart : HorizontalBarChart

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
            ratings.add(27f) // 1★
            ratings.add(45f) // 2★
            ratings.add(65f) // 3★
            ratings.add(77f) // 4★
            ratings.add(93f) // 5★
            setGraphData(ratings)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
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
        entries.add(BarEntry(0f, ratings[0])) // 1★
        entries.add(BarEntry(1f, ratings[1])) // 2★
        entries.add(BarEntry(2f, ratings[2])) // 3★
        entries.add(BarEntry(3f, ratings[3])) // 4★
        entries.add(BarEntry(4f, ratings[4])) // 5★

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
        chart.setDrawValueAboveBar(false)
        chart.setTouchEnabled(true)

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
        xAxis.textSize = 14f

        val yRight = chart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false
        yRight.axisMaximum = 0f
        yRight.axisMinimum = 0f

        chart.animateY(1000)
    }
}
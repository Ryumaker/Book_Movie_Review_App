package com.toyproject.bookandmoviereview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class RecyclerViewBookReviewAdapter constructor(private val context: Context, private val bookReviewList: List<BookReviewData>) : RecyclerView.Adapter<RecyclerViewBookReviewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNickname: TextView = itemView.findViewById(R.id.textNickname)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val textUploadDate: TextView = itemView.findViewById(R.id.textUploadDate)
        val textReview: TextView = itemView.findViewById(R.id.textReview)
        val textNumberOfThumbUp: TextView = itemView.findViewById(R.id.textNumberOfThumbUp)
        val layoutReviewListGridItem: CardView = itemView.findViewById(R.id.layoutReviewListGridItem)
    }

    override fun getItemCount(): Int {
        return bookReviewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_list_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        holder.textNickname.text = bookReviewList[position].nickname
        holder.textUploadDate.text = bookReviewList[position].uploadDate.format(timeFormatter)
        holder.textReview.text = bookReviewList[position].review
        holder.textNumberOfThumbUp.text = bookReviewList[position].numberOfThumbUp.toString()
        holder.ratingBar.rating = bookReviewList[position].rating

        holder.layoutReviewListGridItem.setOnClickListener {
            Toast.makeText(context, bookReviewList[position].nickname, Toast.LENGTH_SHORT).show()
        }
    }
}
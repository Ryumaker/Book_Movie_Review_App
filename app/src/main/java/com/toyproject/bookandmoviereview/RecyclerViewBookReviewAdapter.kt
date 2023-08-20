package com.toyproject.bookandmoviereview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class RecyclerViewBookReviewAdapter constructor(private val context: Context, private val bookReviewList: List<BookReviewData>, private val title: String?) : RecyclerView.Adapter<RecyclerViewBookReviewAdapter.ViewHolder>() {
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
        holder.ratingBar.rating = bookReviewList[position].score

        holder.layoutReviewListGridItem.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookReviewActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("nickname", bookReviewList[position].nickname)
            intent.putExtra("uploadDate", bookReviewList[position].uploadDate.format(timeFormatter))
            intent.putExtra("review", bookReviewList[position].review)
            intent.putExtra("numberOfThumbUp", bookReviewList[position].numberOfThumbUp.toString())
            intent.putExtra("score", bookReviewList[position].score)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }
}
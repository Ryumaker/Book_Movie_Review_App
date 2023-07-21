package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class RecyclerViewBookAdapter constructor(private val getFragment: HomeBookFragment, private val bookList: List<BookData>) : RecyclerView.Adapter<RecyclerViewBookAdapter.ViewHolder>() {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())
    private var image: Bitmap? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textListTitle: TextView = itemView.findViewById(R.id.textListTitle)
        val textListAuthor: TextView = itemView.findViewById(R.id.textListAuthor)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val textNumberOfComments: TextView = itemView.findViewById(R.id.textNumberOfComments)
        val imageBook: ImageView = itemView.findViewById(R.id.imageBook)
        val layoutListItem: CardView = itemView.findViewById(R.id.layoutListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textListTitle.text = bookList[position].title
        holder.textListAuthor.text = bookList[position].author
        holder.ratingBar.rating = bookList[position].rating
        holder.textNumberOfComments.text = "(" + bookList[position].numberOfComments.toString() + ")"

        executor.execute {
            val imageUrl = bookList[position].imageUrl
            try {
                val `in` = java.net.URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    holder.imageBook.setImageBitmap(image)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        // holder.imageBook.setImageResource(bookList[position].image)

        holder.layoutListItem.setOnClickListener {
            Toast.makeText(getFragment.requireContext(), bookList[position].title, Toast.LENGTH_SHORT).show()
        }
    }
}
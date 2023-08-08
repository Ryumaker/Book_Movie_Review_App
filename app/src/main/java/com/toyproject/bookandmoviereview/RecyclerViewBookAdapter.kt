package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewBookAdapter constructor(private val getFragment: HomeBookFragment, private val bookList: List<BookData>) : RecyclerView.Adapter<RecyclerViewBookAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textListTitle: TextView = itemView.findViewById(R.id.textListTitle)
        val textListAuthor: TextView = itemView.findViewById(R.id.textListAuthor)
        val textRating: TextView = itemView.findViewById(R.id.textRating)
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
        holder.textRating.text = bookList[position].rating.toString()
        holder.textNumberOfComments.text = "(" + bookList[position].numberOfComments.toString() + ")"

        // Image load by imageUrl
        Glide.with(holder.itemView.context).load(bookList[position].imageUrl).into(holder.imageBook)

        holder.layoutListItem.setOnClickListener {
            Toast.makeText(getFragment.requireContext(), bookList[position].title, Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, BookReviewListActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }
}
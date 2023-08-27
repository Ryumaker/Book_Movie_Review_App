package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.models.DiscussionBookData
import java.time.format.DateTimeFormatter

class RecyclerViewDiscussionBoardBookAdapter constructor(
    private val getFragment: DiscussionBoardBookFragment,
    private val discussionBookList: List<DiscussionBookData>) : RecyclerView.Adapter<RecyclerViewDiscussionBoardBookAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBookTitle: TextView = itemView.findViewById(R.id.textBookTitle)
        val textUploadDate: TextView = itemView.findViewById(R.id.textUploadDate)
        val textDiscussionBookTitle: TextView = itemView.findViewById(R.id.textDiscussionBookTitle)
        val textNickname: TextView = itemView.findViewById(R.id.textNickname)
        val textNumberOfComments: TextView = itemView.findViewById(R.id.textNumberOfComments)
        val layoutListItem: CardView = itemView.findViewById(R.id.layoutListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discussion_board_book_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return discussionBookList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        holder.textBookTitle.text = discussionBookList[position].bookTitle
        holder.textUploadDate.text = discussionBookList[position].uploadDate.format(timeFormatter)
        holder.textDiscussionBookTitle.text = discussionBookList[position].discussionTitle
        holder.textNickname.text = discussionBookList[position].nickname
        holder.textNumberOfComments.text = "  ${discussionBookList[position].numberOfComments}"

        holder.layoutListItem.setOnClickListener {
            Toast.makeText(holder.itemView.context, discussionBookList[position].bookTitle, Toast.LENGTH_SHORT).show()
        }
    }
}
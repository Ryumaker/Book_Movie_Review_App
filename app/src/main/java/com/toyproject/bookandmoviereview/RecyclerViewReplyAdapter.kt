package com.toyproject.bookandmoviereview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.models.ReplyData
import java.time.format.DateTimeFormatter

class RecyclerViewReplyAdapter constructor(private val context: Context, private val replyList: List<ReplyData>)  : RecyclerView.Adapter<RecyclerViewReplyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textReplierId: TextView = itemView.findViewById(R.id.textReplierId)
        val textUploadDate: TextView = itemView.findViewById(R.id.textUploadDate)
        val textComment: TextView = itemView.findViewById(R.id.textComment)
    }

    override fun getItemCount(): Int {
        return replyList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.reply_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        holder.textReplierId.text = replyList[position].nickname
        holder.textUploadDate.text = replyList[position].uploadDate.format(timeFormatter)
        holder.textComment.text = replyList[position].comment
    }
}
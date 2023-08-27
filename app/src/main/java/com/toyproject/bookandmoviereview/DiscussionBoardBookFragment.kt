package com.toyproject.bookandmoviereview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toyproject.bookandmoviereview.databinding.FragmentDiscussionBoardBookBinding
import com.toyproject.bookandmoviereview.models.DiscussionBookData
import java.time.LocalDate
import java.util.PriorityQueue

class DiscussionBoardBookFragment : Fragment() {
    private var _binding: FragmentDiscussionBoardBookBinding? = null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null
    private var recyclerViewDiscussionBoardBookAdapter: RecyclerViewDiscussionBoardBookAdapter? = null
    private var discussionBookList =  mutableListOf<DiscussionBookData>()

    data class ComparableUploadDate(var discussionBookData: DiscussionBookData) : Comparable<ComparableUploadDate> {
        override fun compareTo(other: ComparableUploadDate): Int {
            return if (other.discussionBookData.uploadDate > discussionBookData.uploadDate) {
                1
            } else if (other.discussionBookData.uploadDate < discussionBookData.uploadDate) {
                -1
            } else {
                0
            }
        }
    }

    data class ComparableComments(var discussionBookData: DiscussionBookData) : Comparable<ComparableComments> {
        override fun compareTo(other: ComparableComments): Int {
            return other.discussionBookData.numberOfComments - discussionBookData.numberOfComments
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareDiscussionBookListData() {
        val pq = PriorityQueue<ComparableUploadDate>()

        var discussionBookData = DiscussionBookData("부의 인문학", "Fusce vel risus a ligula porttitor varius.", "Helene Moore", LocalDate.of(2023, 7, 5), 10)
        pq.add(ComparableUploadDate(discussionBookData))

        discussionBookData = DiscussionBookData("부의 인문학", "Suspendisse varius tellus a vulputate pulvinar.", "Kate Doe", LocalDate.of(2023, 7, 6), 5)
        pq.add(ComparableUploadDate(discussionBookData))

        discussionBookData = DiscussionBookData("부의 인문학", "Nunc pulvinar libero sit amet turpis suscipit blandit.", "Proin sodales", LocalDate.of(2023, 7, 7), 4)
        pq.add(ComparableUploadDate(discussionBookData))

        discussionBookData = DiscussionBookData("부의 인문학", "Nunc dignissim ipsum in ante rhoncus, ut tristique metus tincidunt.", "Ut commodo arcu", LocalDate.of(2023, 7, 8), 2)
        pq.add(ComparableUploadDate(discussionBookData))

        discussionBookData = DiscussionBookData("부의 인문학", "Praesent a nisi tempus erat consectetur varius at non nisl.", "Suspendisse convallis", LocalDate.of(2023, 7, 9), 0)
        pq.add(ComparableUploadDate(discussionBookData))

        while (pq.isNotEmpty()) {
            pq.poll()?.let { discussionBookList.add(it.discussionBookData) }
        }

        recyclerViewDiscussionBoardBookAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortDiscussionBookListByMostRecentUploadDate() {
        val pq = PriorityQueue<ComparableUploadDate>()

        for (discussion in discussionBookList) {
            pq.add(ComparableUploadDate(discussion))
        }

        discussionBookList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { discussionBookList.add(it.discussionBookData) }
        }

        recyclerViewDiscussionBoardBookAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortDiscussionBookListByMostComments() {
        val pq = PriorityQueue<ComparableComments>()

        for (discussion in discussionBookList) {
            pq.add(ComparableComments(discussion))
        }

        discussionBookList.clear()

        while (pq.isNotEmpty()) {
            pq.poll()?.let { discussionBookList.add(it.discussionBookData) }
        }

        recyclerViewDiscussionBoardBookAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscussionBoardBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = resources.getStringArray(R.array.discussion_list_sort_method)
        val sortMethodSpinner = binding.sortMethodSpinner
        val sortMethodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        sortMethodSpinner.adapter = sortMethodAdapter
        sortMethodSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> { // 최신순
                        sortDiscussionBookListByMostRecentUploadDate()
                    }
                    1 -> { // 최다 댓글순
                        sortDiscussionBookListByMostComments()
                    }
                    else -> { // 최신순
                        sortDiscussionBookListByMostRecentUploadDate()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        sortMethodSpinner.setSelection(0) // 0번째 아이템(최신순)이 기본적으로 선택되도록 설정

        recyclerView = binding.recyclerView
        recyclerViewDiscussionBoardBookAdapter = RecyclerViewDiscussionBoardBookAdapter(this@DiscussionBoardBookFragment, discussionBookList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewDiscussionBoardBookAdapter
        prepareDiscussionBookListData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
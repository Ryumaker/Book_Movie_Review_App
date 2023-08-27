package com.toyproject.bookandmoviereview.models

import java.time.LocalDate

data class DiscussionBookData(var bookTitle: String, var discussionTitle: String, var nickname: String, var uploadDate: LocalDate, var numberOfComments: Int)

package com.toyproject.bookandmoviereview

import java.time.LocalDate

data class BookReviewData(var nickname: String, var score: Float, var uploadDate: LocalDate, var review: String, var numberOfThumbUp: Int)

package com.toyproject.bookandmoviereview

import java.time.LocalDate

data class BookReviewData(var nickname: String, var rating: Float, var uploadDate: LocalDate, var review: String, var numberOfThumbUp: Int)

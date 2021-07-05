package com.raindragonn.firestorecommerce.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.raindragonn.firestorecommerce.data.api.ReviewApi
import com.raindragonn.firestorecommerce.data.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepositoryImpl(
    private val reviewApi: ReviewApi,
) : ReviewRepository {
    override suspend fun getLimitProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot?,
        limit: Long
    ): QuerySnapshot = withContext(Dispatchers.IO) {
        reviewApi.getLimitProductReviewSnapshot(productId, lastDocumentSnapshot, limit)
    }

    override suspend fun getLatestProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot?,
    ): List<Review> = withContext(Dispatchers.IO) {
        reviewApi.getLatestProductReviewSnapshot(productId, lastDocumentSnapshot)
    }

    override suspend fun addReview(review: Review): Review = withContext(Dispatchers.IO) {
        reviewApi.addReview(review)
    }

    override suspend fun removeReview(review: Review) = withContext(Dispatchers.IO) {
        reviewApi.removeReview(review)
    }
}

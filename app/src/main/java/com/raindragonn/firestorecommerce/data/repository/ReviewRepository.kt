package com.raindragonn.firestorecommerce.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.raindragonn.firestorecommerce.data.model.Review


interface ReviewRepository {

    suspend fun getLimitProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot? = null,
        limit: Long
    ): QuerySnapshot

    suspend fun getLatestProductReviewSnapshot(
        productId: String,
        lastDocumentSnapshot: DocumentSnapshot? = null
    ): List<Review>

    suspend fun addReview(review: Review): Review

    suspend fun removeReview(review: Review)
}

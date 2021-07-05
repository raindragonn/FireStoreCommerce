package com.raindragonn.firestorecommerce.data.model


import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product(
    @DocumentId
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val price: Int? = null,
    val image: String? = null,
    val publisher: String? = null,
    val pubdate: String? = null,
    val description: String? = null,
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isSelling: Boolean? = null,
    val totalStar: Float? = null,
    val totalReviewer: Int? = null
) : Parcelable
package com.raindragonn.firestorecommerce.data


import com.google.firebase.firestore.DocumentId


data class Product(
    @DocumentId
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val price: String? = null,
    val image: String? = null,
    val publisher: String? = null,
    val pubdate: String?,
    val description: String? = null,
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isSelling: Boolean? = null
)
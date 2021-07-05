package com.raindragonn.firestorecommerce.data.sample.response


import com.google.gson.annotations.SerializedName
import com.raindragonn.firestorecommerce.data.model.Product


data class SampleItem(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("publisher")
    val publisher: String? = null,
    @SerializedName("pubdate")
    val pubdate: String?,
    @SerializedName("description")
    val description: String? = null
)

fun SampleItem.toProduct(): Product {
    return Product(
        title = this.title,
        author = this.author,
        price = this.price,
        image = this.image,
        publisher = this.publisher,
        pubdate = this.pubdate,
        description = this.description,
        isSelling = true,
        totalReviewer = 0,
        totalStar = 0f
    )
}
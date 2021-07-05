package com.raindragonn.firestorecommerce.data.sample.response


import com.google.gson.annotations.SerializedName

data class SampleResponse(
    @SerializedName("items")
    val items: List<SampleItem>?
)
package com.raindragonn.firestorecommerce.data.api

import com.raindragonn.firestorecommerce.data.model.Product

interface ProductApi {
    suspend fun getAllProductList(): List<Product>
}
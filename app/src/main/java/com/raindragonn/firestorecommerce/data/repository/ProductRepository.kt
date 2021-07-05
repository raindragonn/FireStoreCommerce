package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.model.Product

interface ProductRepository {
    suspend fun getAllProductList(): List<Product>
}
package com.raindragonn.firestorecommerce.data.repository

import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.api.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun getAllProductList(): List<Product> = withContext(Dispatchers.IO) {
        productApi.getAllProductList()
    }
}
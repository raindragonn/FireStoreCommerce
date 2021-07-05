package com.raindragonn.firestorecommerce.ui.product_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.repository.ProductRepository
import com.raindragonn.firestorecommerce.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val repository: ProductRepository
) : BaseViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>>
        get() = _productList


    fun getProductData() = viewModelScope.launch {
        startLoading()
        val list = repository.getAllProductList()
        _productList.value = list
        stopLoading()
    }
}
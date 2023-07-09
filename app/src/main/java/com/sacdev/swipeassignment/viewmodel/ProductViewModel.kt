package com.sacdev.swipeassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sacdev.swipeassignment.model.ProductModel
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<ApiResponse<List<ProductModel>>>()
    val products: LiveData<ApiResponse<List<ProductModel>>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = ApiResponse.Loading

            val response = productRepository.getProducts()
            _products.value = response
        }
    }
}
package com.sacdev.swipeassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sacdev.swipeassignment.model.AddProductModel
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.repository.AddProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AddProductViewModel(private var addProductRepository : AddProductRepository) :ViewModel() {
    private val _addProductResult = MutableLiveData<ApiResponse<AddProductModel?>>()
    val addProductResult: LiveData<ApiResponse<AddProductModel?>>
        get() = _addProductResult
    fun submitProduct(
        productName: String,
        productType: String,
        productPrice: String,
        productTax: String,
        imageFile: List<File>
    ) {
        viewModelScope.launch {
            _addProductResult.value = ApiResponse.Loading
            val result = addProductRepository.addProduct(productName, productType, productPrice, productTax, imageFile)
            _addProductResult.postValue(result)
        }
    }
}
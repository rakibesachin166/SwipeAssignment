package com.sacdev.swipeassignment.repository

import com.sacdev.swipeassignment.model.ProductModel
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.network.ApiService

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(): ApiResponse<List<ProductModel>> {
        return try {
            val response = apiService.getProducts()
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}


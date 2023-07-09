package com.sacdev.swipeassignment.repository


import com.sacdev.swipeassignment.model.AddProductModel
import com.sacdev.swipeassignment.network.ApiResponse
import com.sacdev.swipeassignment.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class AddProductRepository(private val apiService: ApiService) {

    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        imageFiles: List<File>
    ): ApiResponse<AddProductModel?> {
        val imageParts: List<MultipartBody.Part> = imageFiles.map { file ->
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files[]", file.name, requestFile)
        }
        val productNameRequest = productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val productTypeRequest = productType.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceRequest = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val taxRequest = tax.toRequestBody("text/plain".toMediaTypeOrNull())
        return try {
            val response = apiService.addProduct(productNameRequest, productTypeRequest, priceRequest, taxRequest, imageParts)
            if (response.isSuccessful) {
                ApiResponse.Success(response.body())
            } else {
                ApiResponse.Error(response.code().toString()+response.message())
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.message ?: "Unknown error occurred")
        }
    }
}

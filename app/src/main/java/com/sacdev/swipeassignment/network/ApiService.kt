package com.sacdev.swipeassignment.network

import com.sacdev.swipeassignment.model.AddProductModel
import com.sacdev.swipeassignment.model.ProductModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("get")
    suspend fun getProducts(): List<ProductModel>


    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody?,
        @Part("product_type") productType: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("tax") tax: RequestBody?,
        @Part image: List<MultipartBody.Part>
    ): Response<AddProductModel>
}

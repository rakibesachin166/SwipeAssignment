package com.sacdev.swipeassignment.model

import com.google.gson.annotations.SerializedName

data class AddProductModel(
    @SerializedName("message") val message: String ,
    @SerializedName("product_details") val product_details: ProductModel,
    @SerializedName("product_id") val product_id: Int,
    @SerializedName("success") val success: Boolean
)


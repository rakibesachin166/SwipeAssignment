package com.sacdev.swipeassignment.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("image") var image: String = "",
    @SerializedName("price") var price: Double? = null,
    @SerializedName("product_name") var productName: String = "",
    @SerializedName("product_type") var productType: String = "",
    @SerializedName("tax") var tax: Double? = null
)

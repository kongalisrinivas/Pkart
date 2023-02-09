package com.example.pkart.model

data class AddProductModel(
    var productName: String? = "",
    var productDescription: String? = "",
    var productCoverImg: String? = "",
    var productCategory: String? = "",
    var productId: String? = "",
    var productMRP: String? = "",
    var productSP: String? = "",
    var productImages: ArrayList<String> = ArrayList()
)

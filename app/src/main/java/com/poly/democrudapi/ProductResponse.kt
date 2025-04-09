package com.poly.democrudapi

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: String,
)

//chuyen doi 1 doi tuong ProductResponse thanh 1 Product
fun ProductResponse.toProduct(): Product{
    return Product(
        id = this.id,
        name = this.name,
        image = this.image,
        price = this.price,
    )
}
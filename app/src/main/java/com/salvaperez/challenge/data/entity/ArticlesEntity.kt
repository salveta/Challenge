package com.salvaperez.challenge.data.entity

import com.google.gson.annotations.SerializedName

data class ArticlesEntity(
    @SerializedName("products") val products: List<ProductEntity>
)
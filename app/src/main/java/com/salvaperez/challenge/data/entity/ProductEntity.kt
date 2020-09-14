package com.salvaperez.challenge.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProductEntity(@PrimaryKey @SerializedName("code") val code: String,
                         @SerializedName("name") val name: String,
                         @SerializedName("price") val price: Double)
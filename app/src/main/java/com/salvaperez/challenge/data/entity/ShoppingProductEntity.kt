package com.salvaperez.challenge.data.entity

data class ShoppingProductEntity(val code: String,
                                 val name: String,
                                 var price: Double,
                                 var units: Int)
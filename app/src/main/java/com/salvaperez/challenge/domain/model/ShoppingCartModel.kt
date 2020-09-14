package com.salvaperez.challenge.domain.model

data class ShoppingCartModel(val product: List<ShoppingProductModel>,
                              val totalPrice: Double)
package com.salvaperez.challenge.data.datasource

import com.salvaperez.challenge.data.entity.ShoppingProductEntity
import com.salvaperez.challenge.domain.model.ShoppingCartModel

interface ShoppingDataSource {

    suspend fun addItemShoppingCart(item: ShoppingProductEntity)

    suspend fun clearItemsShoppingCart()

    fun createCheckout(): ShoppingCartModel
}
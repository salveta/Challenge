package com.salvaperez.challenge.domain.repository

import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.model.ShoppingCartModel

interface ShoppingCartRepository {

    suspend fun getShoppingCart(): ShoppingCartModel

    suspend fun addItemShoppingCart(item: ProductsModel)

    suspend fun clearItemsShoppingCart()

}
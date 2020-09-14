package com.salvaperez.challenge.data.repository

import com.salvaperez.challenge.data.datasource.LocalShoppingDataSource
import com.salvaperez.challenge.domain.model.*
import com.salvaperez.challenge.domain.repository.ShoppingCartRepository

class ShoppingCartDataRepository(private val localShoppingDataSource: LocalShoppingDataSource): ShoppingCartRepository{

    override suspend fun getShoppingCart(): ShoppingCartModel {
        return localShoppingDataSource.createCheckout()
    }

    override suspend fun addItemShoppingCart(item: ProductsModel) {
        localShoppingDataSource.addItemShoppingCart(item.toShoppingCartEntity())
    }

    override suspend fun clearItemsShoppingCart() {
        localShoppingDataSource.clearItemsShoppingCart()
    }
}

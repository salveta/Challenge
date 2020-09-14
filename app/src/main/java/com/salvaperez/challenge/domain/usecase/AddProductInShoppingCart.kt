package com.salvaperez.challenge.domain.usecase

import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.repository.ShoppingCartRepository

class AddProductInShoppingCart(private var shoppingCartRepository: ShoppingCartRepository) {

    suspend operator fun invoke(
        item: ProductsModel
    ) = shoppingCartRepository.addItemShoppingCart(item)

}
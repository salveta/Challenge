package com.salvaperez.challenge.domain.usecase

import com.salvaperez.challenge.domain.repository.ShoppingCartRepository

class GetShoppingCart(private var shoppingCartRepository: ShoppingCartRepository) {

    suspend operator fun invoke() = shoppingCartRepository.getShoppingCart()

}
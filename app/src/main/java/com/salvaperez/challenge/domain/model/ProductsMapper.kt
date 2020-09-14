package com.salvaperez.challenge.domain.model

import com.salvaperez.challenge.data.entity.ProductEntity
import com.salvaperez.challenge.data.entity.ShoppingProductEntity

fun ProductEntity.toModel(): ProductsModel{
    return ProductsModel(
        name = name,
        code = code,
        price = price
    )
}

fun ProductsModel.toEntity(): ProductEntity{
    return ProductEntity(
        name = name,
        code = code,
        price = price
    )
}

fun ProductsModel.toShoppingCartEntity(): ShoppingProductEntity {
    return ShoppingProductEntity(
        name = name,
        code = code,
        price = price,
        units = 0
    )
}

fun ShoppingProductEntity.toShoppingCartModel(): ShoppingProductModel{
    return ShoppingProductModel(
        name = name,
        code = code,
        price = price,
        units = units
    )
}
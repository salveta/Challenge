package com.salvaperez.challenge.data.database

import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.data.datasource.LocalProductsDataSource
import com.salvaperez.challenge.data.entity.ErrorEntity
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.model.toEntity
import com.salvaperez.challenge.domain.model.toModel

class RoomProductsDataSource(db: ProductDatabase) : LocalProductsDataSource {

    private val productDao = db.productDao()

    override suspend fun getProducts(): ChallengeResult<ErrorEntity, List<ProductsModel>> {
        val productEntity = productDao.getAllProducts()
        return ChallengeResult.Success(productEntity.map { it.toModel() })
    }

    override suspend fun saveProducts(products: List<ProductsModel>) {
        productDao.insertProducts(products.map { it.toEntity() })
    }
}
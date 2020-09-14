package com.salvaperez.challenge.data.datasource

import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.data.entity.ErrorEntity
import com.salvaperez.challenge.domain.model.ProductsModel

interface LocalProductsDataSource {

    suspend fun getProducts(): ChallengeResult<ErrorEntity, List<ProductsModel>>

    suspend fun saveProducts(products: List<ProductsModel>)
}
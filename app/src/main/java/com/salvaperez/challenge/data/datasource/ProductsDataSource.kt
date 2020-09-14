package com.salvaperez.challenge.data.datasource

import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.data.entity.ErrorEntity
import com.salvaperez.challenge.data.entity.ProductEntity

interface ProductsDataSource {

    suspend fun getProducts(): ChallengeResult<ErrorEntity, List<ProductEntity>>

}
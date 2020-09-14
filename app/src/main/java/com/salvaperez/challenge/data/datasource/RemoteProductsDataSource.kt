package com.salvaperez.challenge.data.datasource

import com.salvaperez.challenge.data.api.ApiCall
import com.salvaperez.challenge.data.api.ChallengeApi
import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.data.entity.ErrorEntity
import com.salvaperez.challenge.data.entity.ProductEntity

class RemoteProductsDataSource(private val api: ChallengeApi): ProductsDataSource {

    override suspend fun getProducts(): ChallengeResult<ErrorEntity, List<ProductEntity>> {
        return ApiCall.safeApiCall { api.getProducts().products }
    }
}
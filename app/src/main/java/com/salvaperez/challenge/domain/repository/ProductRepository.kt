package com.salvaperez.challenge.domain.repository

import com.salvaperez.challenge.data.api.ChallengeError
import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.domain.model.ProductsModel

interface ProductRepository {

    suspend fun getProducts(): ChallengeResult<ChallengeError, List<ProductsModel>>

}
package com.salvaperez.challenge.domain.usecase

import com.salvaperez.challenge.data.api.ChallengeError
import com.salvaperez.challenge.data.api.fold
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProducts(private val productRepository: ProductRepository) {

    suspend operator fun invoke(
        onGetProductSuccess: (data: List<ProductsModel>) -> Unit,
        onGetErrorProduct: (data: ChallengeError) -> Unit
    ) {

        val result = withContext(Dispatchers.IO){
            productRepository.getProducts()
        }

        result.fold(
            failure = { error -> onGetErrorProduct(error) },
            success = { data -> onGetProductSuccess(data) }
        )
    }
}
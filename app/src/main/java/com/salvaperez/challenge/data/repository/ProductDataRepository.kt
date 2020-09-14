package com.salvaperez.challenge.data.repository

import android.content.Context
import com.salvaperez.challenge.data.api.ChallengeError
import com.salvaperez.challenge.data.api.ChallengeResult
import com.salvaperez.challenge.data.api.fold
import com.salvaperez.challenge.data.database.RoomProductsDataSource
import com.salvaperez.challenge.data.datasource.RemoteProductsDataSource
import com.salvaperez.challenge.domain.model.ProductsModel
import com.salvaperez.challenge.domain.model.toChallengeError
import com.salvaperez.challenge.domain.model.toModel
import com.salvaperez.challenge.domain.repository.ProductRepository
import com.salvaperez.challenge.presentation.extensions.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDataRepository(private val remoteProductsDataSource: RemoteProductsDataSource,
                            private val roomProductsDataSource: RoomProductsDataSource,
                            private val context: Context): ProductRepository {

    override suspend fun getProducts(): ChallengeResult<ChallengeError, List<ProductsModel>> {
        if(context.isInternetAvailable()){
            val result = remoteProductsDataSource.getProducts()
            return result.fold(
                { errorEntity -> ChallengeResult.Failure(errorEntity.toChallengeError()) },
                { dataEntity ->
                    GlobalScope.launch(Dispatchers.IO){
                        roomProductsDataSource.saveProducts(dataEntity.map { it.toModel() })
                    }

                    ChallengeResult.Success(dataEntity.map { it.toModel() }) }
            )
        }else{
            val result = roomProductsDataSource.getProducts()
            return result.fold(
                {errorEntity ->  ChallengeResult.Failure(errorEntity.toChallengeError())},
                {dataEntity -> ChallengeResult.Success(dataEntity)}
            )
        }
    }
}
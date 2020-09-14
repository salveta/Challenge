package com.salvaperez.challenge.data.api

import com.salvaperez.challenge.data.entity.ErrorEntity
import com.salvaperez.challenge.data.entity.toErrorEntity
import retrofit2.HttpException

object ApiCall {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ChallengeResult<ErrorEntity, T> {
        return try {
            val result = apiCall()
            ChallengeResult.Success(result)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val errorEntity = throwable.toErrorEntity()
                ChallengeResult.Failure(errorEntity)
            } else {
                ChallengeResult.Failure(ErrorEntity.unknownEntity)
            }
        }
    }
}
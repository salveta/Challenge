package com.salvaperez.challenge.data.api

import com.salvaperez.challenge.data.entity.ArticlesEntity
import retrofit2.http.GET
import retrofit2.http.Headers

interface ChallengeApi {

    @Headers("Accept: application/json")
    @GET("Products.json")
    suspend fun getProducts(): ArticlesEntity
}
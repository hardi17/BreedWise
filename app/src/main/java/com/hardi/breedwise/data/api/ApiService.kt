package com.hardi.breedwise.data.api

import com.hardi.breedwise.data.model.AllBreedResult
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("list/all")
    suspend fun fetchAllBreed(): AllBreedResult
}
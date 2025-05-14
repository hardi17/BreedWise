package com.hardi.breedwise.data.api

import com.hardi.breedwise.data.model.AllBreedResult
import com.hardi.breedwise.data.model.BreedImageResult
import com.hardi.breedwise.data.model.SubBreedResult
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("breeds/list/all")
    suspend fun fetchAllBreed(): AllBreedResult

    @GET("breed/{breedName}/list")
    suspend fun fetchSubBreed(
        @Path("breedName") breedName: String
    ): SubBreedResult

    @GET("breed/{breedName}/images")
    suspend fun fetchBreedImages(
        @Path("breedName") breedName: String
    ): BreedImageResult
}
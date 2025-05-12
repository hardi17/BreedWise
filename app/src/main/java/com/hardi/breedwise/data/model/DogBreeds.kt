package com.hardi.breedwise.data.model

import com.google.gson.annotations.SerializedName

data class DogBreeds(
    @SerializedName("breed")
    val breed: String,
    @SerializedName("subBreedList")
    val subBreedList: List<String>? = null
)
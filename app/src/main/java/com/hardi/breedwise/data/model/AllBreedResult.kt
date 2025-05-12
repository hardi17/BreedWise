package com.hardi.breedwise.data.model

import com.google.gson.annotations.SerializedName

data class AllBreedResult(
    @SerializedName("message")
    val message: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)

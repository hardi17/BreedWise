package com.hardi.breedwise.data.model

import com.google.gson.annotations.SerializedName

data class SubBreedResult(
    @SerializedName("message")
    val message: List<String>,
    @SerializedName("status")
    val status: String
)

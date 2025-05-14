package com.hardi.breedwise.data.model

import com.google.gson.annotations.SerializedName

data class BreedImageResult(
    @SerializedName("message")
    val message: List<String>? = null,
    @SerializedName("status")
    val status: String
)

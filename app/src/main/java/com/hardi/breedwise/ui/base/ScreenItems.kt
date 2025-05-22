package com.hardi.breedwise.ui.base

import com.hardi.breedwise.utils.AppConstant.BREED_NAME
import com.hardi.breedwise.utils.AppConstant.SUB_BREED_NAME

sealed class ScreenItems(
    val name: String
) {
    object AllBreedScreen : ScreenItems("AllBreed")
    object SubBreedScreen : ScreenItems("SubBreed/{$BREED_NAME}") {
        fun passArg(breedName: String) = "SubBreed/$breedName"
    }

    object BreedImageScreen : ScreenItems("BreedImage/{$BREED_NAME}/{$SUB_BREED_NAME}") {
        fun passArg(
            breedName: String,
            subBreedName: String
        ) = "BreedImage/$breedName/$subBreedName"
    }
}

val screenObj: List<ScreenItems> = listOf(
    ScreenItems.AllBreedScreen,
    ScreenItems.SubBreedScreen,
    ScreenItems.BreedImageScreen
)



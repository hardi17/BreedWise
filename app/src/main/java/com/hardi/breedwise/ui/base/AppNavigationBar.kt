package com.hardi.breedwise.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hardi.breedwise.ui.allbreed.AllBreedScreen
import com.hardi.breedwise.ui.breedImages.BreedImagesScreen
import com.hardi.breedwise.ui.subbreeds.SubBreedScreen
import com.hardi.breedwise.utils.AppConstant.BREED_NAME
import com.hardi.breedwise.utils.AppConstant.SUB_BREED_NAME

@Composable
fun AppNavigationBar() {

    val navHostController = rememberNavController()
    //val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingVal: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = ScreenItems.AllBreedScreen.name,
            modifier = Modifier.padding(paddingVal)
        ) {
            composable(ScreenItems.AllBreedScreen.name) {
                AllBreedScreen(
                    onBreedClick = {
                        navHostController.navigate(
                            ScreenItems.SubBreedScreen.passArg(it)
                        )
                    }
                )
            }
            composable(
                ScreenItems.SubBreedScreen.name,
                listOf(navArgument(BREED_NAME) { type = NavType.StringType })
            ) {
                val breedName = it.arguments?.getString(BREED_NAME)
                SubBreedScreen(
                    breedName ?: "",
                    onSubBreedClick = { breedName, name ->
                        navHostController.navigate(
                            ScreenItems.BreedImageScreen.passArg(breedName, name)
                        )
                    })
            }
            composable(ScreenItems.BreedImageScreen.name,
                listOf(navArgument(BREED_NAME) { type = NavType.StringType },
                    navArgument(SUB_BREED_NAME) { type = NavType.StringType })
            ){
                val breedName = it.arguments?.getString(BREED_NAME)
                val subBreedName = it.arguments?.getString(SUB_BREED_NAME)
                if (breedName != null) {
                    BreedImagesScreen(
                        breedName,
                        subBreedName ?: ""
                    )
                }
            }
        }
    }

}
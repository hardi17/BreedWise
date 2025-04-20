package com.hardi.breedwise.di.module

import com.hardi.breedwise.ui.allbreed.AllBreedAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import java.util.ArrayList

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideAdapter()= AllBreedAdapter(ArrayList())

}
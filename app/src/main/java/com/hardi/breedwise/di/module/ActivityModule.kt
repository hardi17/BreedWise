package com.hardi.breedwise.di.module

import com.hardi.breedwise.ui.allbreed.AllBreedAdapter
import com.hardi.breedwise.ui.subbreeds.SubBreedAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideAllBreedAdapter()= AllBreedAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideSubBreedAdapter() = SubBreedAdapter(ArrayList())

}
package com.hardi.breedwise.di.module

import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.di.BaseUrl
import com.hardi.breedwise.utils.AppConstant.BASE_URL
import com.hardi.breedwise.utils.DefaultDispatcherProvider
import com.hardi.breedwise.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideApiService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

}
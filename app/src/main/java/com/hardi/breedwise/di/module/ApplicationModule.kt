package com.hardi.breedwise.di.module

import android.content.Context
import androidx.room.Room
import com.hardi.breedwise.data.api.ApiService
import com.hardi.breedwise.data.roomdatabase.AppRoomDataBase
import com.hardi.breedwise.data.roomdatabase.AppRoomDatabaseService
import com.hardi.breedwise.data.roomdatabase.DatabaseService
import com.hardi.breedwise.di.BaseUrl
import com.hardi.breedwise.di.DatabaseName
import com.hardi.breedwise.utils.AppConstant.BASE_URL
import com.hardi.breedwise.utils.AppConstant.DATABASE_NAME
import com.hardi.breedwise.utils.DefaultDispatcherProvider
import com.hardi.breedwise.utils.DispatcherProvider
import com.hardi.breedwise.utils.InternetCheck.InternetConnected
import com.hardi.breedwise.utils.InternetCheck.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) : NetworkHelper{
        return InternetConnected(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = DATABASE_NAME

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppRoomDataBase {
        return Room.databaseBuilder(
            context,
            AppRoomDataBase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appRoomDataBase: AppRoomDataBase) : DatabaseService{
        return AppRoomDatabaseService(appRoomDataBase)
    }
}
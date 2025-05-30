package com.example.marsphotos.di

import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.network.MarsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideMarsApiService(retrofit: Retrofit): MarsApiService {
        return retrofit.create(MarsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMarsPhotosRepository(
        marsApiService: MarsApiService
    ): MarsPhotosRepository {
        return NetworkMarsPhotosRepository(marsApiService)
    }
}

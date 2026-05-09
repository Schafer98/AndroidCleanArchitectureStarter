package com.dev.androidcleanarchitecturestarter.data.di

import com.dev.androidcleanarchitecturestarter.data.WeatherRepositoryImpl
import com.dev.androidcleanarchitecturestarter.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
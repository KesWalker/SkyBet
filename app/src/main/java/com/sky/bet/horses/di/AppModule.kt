package com.sky.bet.horses.di

import com.sky.bet.horses.repo.RacesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRacesRepo(): RacesRepo = RacesRepo()
}
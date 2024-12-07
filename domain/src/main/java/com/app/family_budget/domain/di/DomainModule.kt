package com.app.family_budget.domain.di

import com.app.family_budget.domain.common.AppDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal class DataModule {
    @Provides
    fun provideAppDispatchers(): AppDispatcher {
        return AppDispatcher()
    }
}
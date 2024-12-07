package com.app.family_budget.details.di

import com.app.family_budget.details.api.DetailsFeatureApiImpl
import com.app.family_budget.details_api.DetailsFeatureApi
import com.app.ui.navigation.NavigationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DetailsModule {

    @Provides
    @Singleton
    fun provideDetailsFeatureApi(): DetailsFeatureApi {
        return DetailsFeatureApiImpl()
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideListNavigationFactory(
        listFeatureApi: DetailsFeatureApi
    ): NavigationFactory {
        return listFeatureApi as NavigationFactory
    }
}
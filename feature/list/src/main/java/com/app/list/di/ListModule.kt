package com.app.list.di

import android.content.Context
import com.app.family_budget.list_api.ListFeatureApi
import com.app.list.api.ListFeatureApiImpl
import com.app.ui.navigation.NavigationFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class ListModule {

    @Provides
    @Singleton
    fun provideCamsListFeatureApi(@ApplicationContext context: Context): ListFeatureApi {
        return ListFeatureApiImpl(context)
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideListNavigationFactory(
        listFeatureApi: ListFeatureApi
    ): NavigationFactory {
        return listFeatureApi as NavigationFactory
    }
}
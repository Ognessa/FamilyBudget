package com.app.list.di

import com.app.family_budget.details_api.DetailsFeatureApi
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DependencyProvider {
    val detailsFeatureApi: DetailsFeatureApi
}
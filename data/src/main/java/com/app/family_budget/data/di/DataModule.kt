package com.app.family_budget.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.app.family_budget.data.client.GithubApiClient
import com.app.family_budget.data.common.ExceptionHandlerImpl
import com.app.family_budget.data.local.AppDatabase
import com.app.family_budget.data.local.dao.RepoDao
import com.app.family_budget.data.remote.GithubApiService
import com.app.family_budget.data.repository.RepositoryImpl
import com.app.family_budget.domain.Repository
import com.app.family_budget.domain.common.AppDispatcher
import com.app.family_budget.domain.common.ExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun bindRepository(
        githubApiService: GithubApiService,
        repoDao: RepoDao,
        dispatcher: AppDispatcher,
        exceptionHandler: ExceptionHandler
    ): Repository {
        return RepositoryImpl(githubApiService, repoDao, dispatcher, exceptionHandler)
    }

    @Provides
    @Singleton
    fun provideAppSchedulers(@ApplicationContext context: Context): AppDatabase {
        return databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideExceptionHandler(): ExceptionHandler {
        return ExceptionHandlerImpl()
    }

    @Provides
    fun provideGithubApiClient(): GithubApiClient {
        return GithubApiClient()
    }

    @Provides
    fun provideGithubApiService(githubApiClient: GithubApiClient): GithubApiService {
        return githubApiClient.retrofit.create(GithubApiService::class.java)
    }
}


package com.example.apollo_launches.di

import com.example.apollo_launches.data.network.GraphQLService
import com.example.apollo_launches.data.repository.LaunchRepositoryImpl
import com.example.apollo_launches.domain.repository.LaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLaunchRepository(graphQLService: GraphQLService): LaunchRepository {
        return LaunchRepositoryImpl(graphQLService)
    }
}
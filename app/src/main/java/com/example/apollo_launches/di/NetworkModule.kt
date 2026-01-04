package com.example.apollo_launches.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.example.apollo_launches.data.network.GraphQLService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideGraphQLService(apolloClient: ApolloClient): GraphQLService {
        return GraphQLService(apolloClient)
    }
}
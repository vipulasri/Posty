package com.vipulasri.posty.di

import com.apollographql.apollo.ApolloClient
import com.vipulasri.posty.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Vipul Asri on 08/12/21.
 */

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.API_BASE_URL)
            .build()
    }

}
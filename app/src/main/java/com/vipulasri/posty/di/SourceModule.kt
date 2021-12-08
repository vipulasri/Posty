package com.vipulasri.posty.di

import com.apollographql.apollo.ApolloClient
import com.vipulasri.posty.data.remote.IPostRemoteSource
import com.vipulasri.posty.data.remote.PostRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Vipul Asri on 09/12/21.
 */

@InstallIn(SingletonComponent::class)
@Module
object SourceModule {

    @Singleton
    @Provides
    fun providePostRemoteSource(
        apolloClient: ApolloClient
    ): IPostRemoteSource {
        return PostRemoteSource(
            apolloClient,
            Dispatchers.IO
        )
    }

}
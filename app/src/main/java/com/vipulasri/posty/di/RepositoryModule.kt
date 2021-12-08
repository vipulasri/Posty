package com.vipulasri.posty.di

import com.apollographql.apollo.ApolloClient
import com.vipulasri.posty.data.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Created by Vipul Asri on 08/12/21.
 */

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        apolloClient: ApolloClient
    ): PostRepository {
        return PostRepository(
            apolloClient,
            Dispatchers.IO
        )
    }

}
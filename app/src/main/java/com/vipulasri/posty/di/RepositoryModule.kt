package com.vipulasri.posty.di

import com.vipulasri.posty.data.repo.PostRepository
import com.vipulasri.posty.data.remote.IPostRemoteSource
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
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        remoteSource: IPostRemoteSource
    ): PostRepository {
        return PostRepository(remoteSource)
    }

}
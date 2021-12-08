package com.vipulasri.posty.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.awaitSafeResult
import com.vipulasri.posty.type.PageQueryOptions
import com.vipulasri.posty.type.PaginateOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Vipul Asri on 09/12/21.
 */

class PostRemoteSource(
    private val apolloClient: ApolloClient,
    private val dispatcher: CoroutineDispatcher
) : IPostRemoteSource {

    override suspend fun getPosts(
        page: Int,
        limit: Int
    ): SafeResult<GetPostsQuery.Data> {
        return withContext(dispatcher) {
            val options = PageQueryOptions(
                paginate = Input.fromNullable(
                    PaginateOptions(
                        page = Input.fromNullable(page),
                        limit = Input.fromNullable(limit)
                    )
                )
            )
            apolloClient.query(GetPostsQuery(Input.fromNullable(options))).awaitSafeResult()
        }
    }

}

interface IPostRemoteSource {
    suspend fun getPosts(page: Int, limit: Int): SafeResult<GetPostsQuery.Data>
}
package com.vipulasri.posty.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.type.PageQueryOptions
import com.vipulasri.posty.type.PaginateOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Vipul Asri on 08/12/21.
 */

class PostRepository(
    private val apolloClient: ApolloClient,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getPosts(
        page: Int = 1,
        limit: Int = 10
    ): Response<GetPostsQuery.Data> {
        return withContext(dispatcher) {
            val options = PageQueryOptions(
                paginate = Input.fromNullable(
                    PaginateOptions(
                        page = Input.fromNullable(page),
                        limit = Input.fromNullable(limit)
                    )
                )
            )
            apolloClient.query(GetPostsQuery(Input.fromNullable(options))).await()
        }
    }

}
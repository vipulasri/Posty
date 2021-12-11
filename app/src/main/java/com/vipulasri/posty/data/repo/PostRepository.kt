package com.vipulasri.posty.data.repo

import com.vipulasri.posty.GetPostByIdQuery
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.remote.IPostRemoteSource

/**
 * Created by Vipul Asri on 08/12/21.
 */

class PostRepository(
    private val remoteSource: IPostRemoteSource
) {

    suspend fun getPosts(page: Int, limit: Int): SafeResult<GetPostsQuery.Data> {
        return remoteSource.getPosts(page, limit)
    }

    suspend fun getPostById(id: String): SafeResult<GetPostByIdQuery.Data> {
        return remoteSource.getPostById(id)
    }

}
package com.vipulasri.posty.domain.usecase

import com.vipulasri.posty.data.repo.PostRepository
import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.toDomainModel
import com.vipulasri.posty.domain.BaseUseCase
import com.vipulasri.posty.domain.model.Post

/**
 * Created by Vipul Asri on 09/12/21.
 */

class GetPostsUseCase(
    private val postRepository: PostRepository
) : BaseUseCase<GetPostsRequest, SafeResult<List<Post>>> {

    override suspend fun perform(params: GetPostsRequest): SafeResult<List<Post>> {
        return when (val result = postRepository.getPosts(params.page, params.limit)) {
            is SafeResult.Success -> {
                SafeResult.Success(result.data.toDomainModel())
            }
            is SafeResult.Failure -> result
        }
    }

}

data class GetPostsRequest(
    val page: Int = 1,
    val limit: Int = 10
)
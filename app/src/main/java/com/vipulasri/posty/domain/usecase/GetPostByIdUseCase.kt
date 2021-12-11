package com.vipulasri.posty.domain.usecase

import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.repo.PostRepository
import com.vipulasri.posty.data.toDomainModel
import com.vipulasri.posty.domain.BaseUseCase
import com.vipulasri.posty.domain.model.Post

/**
 * Created by Vipul Asri on 09/12/21.
 */

class GetPostByIdUseCase(
    private val postRepository: PostRepository
) : BaseUseCase<String, SafeResult<Post>> {

    override suspend fun perform(params: String): SafeResult<Post> {
        return when (val result = postRepository.getPostById(params)) {
            is SafeResult.Success -> {
                SafeResult.Success(result.data.toDomainModel())
            }
            is SafeResult.Failure -> result
        }
    }

}
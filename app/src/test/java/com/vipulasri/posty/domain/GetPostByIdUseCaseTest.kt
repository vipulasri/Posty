package com.vipulasri.posty.domain

import com.vipulasri.posty.GetPostByIdQuery
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.repo.PostRepository
import com.vipulasri.posty.domain.usecase.GetPostByIdUseCase
import com.vipulasri.posty.domain.usecase.GetPostsRequest
import com.vipulasri.posty.domain.usecase.GetPostsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.lang.Exception

/**
 * Created by Vipul Asri on 13/12/21.
 */

class GetPostByIdUseCaseTest {

    private val repository: PostRepository = mockk()
    private lateinit var getPostByIdUseCase: GetPostByIdUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPostByIdUseCase = GetPostByIdUseCase(repository)
    }

    @Test
    fun `check if post details are available`() = runBlocking {
        val postId = "1"

        coEvery { repository.getPostById(postId)} returns SafeResult.Success(
            GetPostByIdQuery.Data(
                post = GetPostByIdQuery.Post(
                    id = "1",
                    title = "title 1",
                    body = "body 1",
                    user = GetPostByIdQuery.User(
                        id = "1",
                        name = "John Doe",
                        username = "jdoe"
                    )
                )
            )
        )

        val postsResult = getPostByIdUseCase.perform(postId)

        assert(postsResult is SafeResult.Success)
        assert((postsResult as SafeResult.Success).data.id == postId)
    }

    @Test
    fun `check for internet connection error`() = runBlocking {
        val postId = "1"
        val error = Exception("No Internet Connection!")

        coEvery { repository.getPostById(postId) } returns SafeResult.Failure(
            error
        )

        val postsResult = getPostByIdUseCase.perform(postId)

        assert(postsResult is SafeResult.Failure)
        assert((postsResult as SafeResult.Failure).exception == error)
    }

}

package com.vipulasri.posty.domain

import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.data.SafeResult
import com.vipulasri.posty.data.repo.PostRepository
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

class GetPostsUseCaseTest {

    private val repository: PostRepository = mockk()
    private lateinit var getPostsUseCase: GetPostsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getPostsUseCase = GetPostsUseCase(repository)
    }

    @Test
    fun `check if posts are empty`() = runBlocking {
        val request = GetPostsRequest()
        coEvery { repository.getPosts(request.page, request.limit) } returns SafeResult.Success(
            GetPostsQuery.Data(
                GetPostsQuery.Page(
                    posts = emptyList<GetPostsQuery.Post>(),
                    meta = null
                )
            )
        )

        val postsResult = getPostsUseCase.perform(request)

        assert(postsResult is SafeResult.Success)
        assert((postsResult as SafeResult.Success).data.isEmpty())
    }

    @Test
    fun `check posts are available`() = runBlocking {
        val request = GetPostsRequest()
        val posts = preparePostsFromServer()

        coEvery { repository.getPosts(request.page, request.limit) } returns SafeResult.Success(
            GetPostsQuery.Data(
                GetPostsQuery.Page(
                    posts = posts,
                    meta = null
                )
            )
        )

        val postsResult = getPostsUseCase.perform(request)

        assert(postsResult is SafeResult.Success)
        assert((postsResult as SafeResult.Success).data.size == posts.size)
    }

    private fun preparePostsFromServer(): List<GetPostsQuery.Post> {
        return listOf(
            GetPostsQuery.Post(id = "1", title = "title 1", body = "body 1"),
            GetPostsQuery.Post(id = "2", title = "title 2", body = "body 2"),
            GetPostsQuery.Post(id = "3", title = "title 3", body = "body 3"),
            GetPostsQuery.Post(id = "4", title = "title 4", body = "body 4")
        )
    }

    @Test
    fun `check for internet connection error`() = runBlocking {
        val request = GetPostsRequest()
        val error = Exception("No Internet Connection!")

        coEvery { repository.getPosts(request.page, request.limit) } returns SafeResult.Failure(
            error
        )

        val postsResult = getPostsUseCase.perform(request)

        assert(postsResult is SafeResult.Failure)
        assert((postsResult as SafeResult.Failure).exception == error)
    }

}

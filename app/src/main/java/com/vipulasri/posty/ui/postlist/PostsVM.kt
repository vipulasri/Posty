package com.vipulasri.posty.ui.postlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vipulasri.posty.data.handleResult
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.domain.usecase.GetPostsRequest
import com.vipulasri.posty.domain.usecase.GetPostsUseCase
import com.vipulasri.posty.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Vipul Asri on 08/12/21.
 */

@HiltViewModel
class PostsVM @Inject constructor(
    private val postsUseCase: GetPostsUseCase
) : BaseVM() {

    var viewState by mutableStateOf<PostsViewState>(PostsViewState.None)
        private set

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            viewState = PostsViewState.Loading
            val response = postsUseCase.perform(GetPostsRequest())

            response.handleResult(
                onSuccess = { posts ->
                    viewState = PostsViewState.Success(posts)
                },
                onError = { error ->
                    viewState = PostsViewState.Error(error.message)
                }
            )
        }
    }

}

sealed class PostsViewState {
    object None : PostsViewState()
    object Loading : PostsViewState()
    class Success(val posts: List<Post>) : PostsViewState()
    class Error(val message: String?) : PostsViewState()
}
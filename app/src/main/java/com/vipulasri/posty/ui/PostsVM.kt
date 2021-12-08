package com.vipulasri.posty.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.data.PostRepository
import com.vipulasri.posty.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Vipul Asri on 08/12/21.
 */

@HiltViewModel
class PostsVM @Inject constructor(
    private val postRepository: PostRepository
) : BaseVM() {

    var viewState by mutableStateOf<PostsViewState>(PostsViewState.None)
        private set

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            viewState = PostsViewState.Loading
            val response = postRepository.getPosts()
            Timber.d("response: ${response.data?.toString() ?: ""}")

            response.data?.page?.posts?.let {
                viewState = PostsViewState.Success(
                    response.data?.page?.posts?.filterNotNull() ?: emptyList()
                )
            }

            if (response.data?.page?.posts == null || response.hasErrors()) {
                viewState = PostsViewState.Error(
                    response.errors?.toString()
                )
            }
        }
    }

}

sealed class PostsViewState {
    object None : PostsViewState()
    object Loading : PostsViewState()
    class Success(val posts: List<GetPostsQuery.Post>) : PostsViewState()
    class Error(val message: String?) : PostsViewState()
}
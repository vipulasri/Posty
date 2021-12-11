package com.vipulasri.posty.ui.postdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vipulasri.posty.data.handleResult
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.domain.usecase.GetPostByIdUseCase
import com.vipulasri.posty.ui.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Vipul Asri on 10/12/21.
 */

@HiltViewModel
class PostDetailsVM @Inject constructor(
    private val postUseCase: GetPostByIdUseCase
) : BaseVM() {

    var viewState by mutableStateOf<PostDetailsViewState>(PostDetailsViewState.None)
        private set

    fun getPostDetails(id: String) {
        viewModelScope.launch {
            viewState = PostDetailsViewState.Loading
            val response = postUseCase.perform(id)

            response.handleResult(
                onSuccess = { post ->
                    viewState = PostDetailsViewState.Success(post)
                },
                onError = { error ->
                    viewState = PostDetailsViewState.Error(error.message)
                }
            )
        }
    }

}

sealed class PostDetailsViewState {
    object None : PostDetailsViewState()
    object Loading : PostDetailsViewState()
    class Success(val post: Post) : PostDetailsViewState()
    class Error(val message: String?) : PostDetailsViewState()
}
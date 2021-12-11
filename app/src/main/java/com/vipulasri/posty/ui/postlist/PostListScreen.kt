package com.vipulasri.posty.ui.postlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vipulasri.posty.R
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.ui.DEFAULT_PADDING
import com.vipulasri.posty.ui.Error
import com.vipulasri.posty.ui.Loading

/**
 * Created by Vipul Asri on 08/12/21.
 */


@Composable
fun PostListScreen(
    viewModel: PostsVM,
    navigateToDetails: (post: Post) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {
        when (val viewState = viewModel.viewState) {
            is PostsViewState.Loading -> {
                Loading()
            }
            is PostsViewState.Success -> {
                PostList(posts = viewState.posts, onPostClick = navigateToDetails)
            }
            is PostsViewState.Error -> {
                Error(message = viewState.message, onRetry = {
                    viewModel.getPosts()
                })
            }
        }
    }
}

@Composable
private fun PostList(
    posts: List<Post>,
    onPostClick: (post: Post) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(posts) { post ->
            PostElement(post, onPostClick)
            Divider()
        }
    }
}

@Composable
private fun PostElement(
    post: Post,
    onPostClick: (post: Post) -> Unit
) {
    Column(
        Modifier
            .clickable {
                onPostClick.invoke(post)
            }
            .padding(DEFAULT_PADDING)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.h6
        )

        val body = post.body.replace("\n", " ")
        if (body.isEmpty()) return

        val bodyText = if (body.length > 120) {
            "${body.substring(0, 120)}..."
        } else body

        Text(
            text = bodyText.trim(),
            style = MaterialTheme.typography.body1
        )
    }
}
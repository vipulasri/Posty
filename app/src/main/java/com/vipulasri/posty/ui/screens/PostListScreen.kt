package com.vipulasri.posty.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.ui.Error
import com.vipulasri.posty.ui.Loading
import com.vipulasri.posty.ui.PostsViewState

/**
 * Created by Vipul Asri on 08/12/21.
 */


@Composable
fun PostListScreen(viewState: PostsViewState, onRetry: (() -> Unit)? = null) {
    when (viewState) {
        is PostsViewState.Loading -> {
            Loading()
        }
        is PostsViewState.Success -> {
            PostList(posts = viewState.posts)
        }
        is PostsViewState.Error -> {
            Error(message = viewState.message, onRetry)
        }
    }
}

@Composable
private fun PostList(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(posts) { post ->
            PostElement(post)
            Divider()
        }
    }
}

@Composable
private fun PostElement(post: Post) {
    Column(
        Modifier.padding(10.dp)
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
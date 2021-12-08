package com.vipulasri.posty.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.ui.PostsViewState

/**
 * Created by Vipul Asri on 08/12/21.
 */


@Composable
fun PostListScreen(viewState: PostsViewState) {
    when (viewState) {
        is PostsViewState.Loading -> {
            Loading()
        }
        is PostsViewState.Success -> {
            PostList(posts = viewState.posts)
        }
        is PostsViewState.Error -> {

        }
    }
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PostList(posts: List<GetPostsQuery.Post>) {
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
private fun PostElement(post: GetPostsQuery.Post) {
    Column(
        Modifier.padding(10.dp)
    ) {
        Text(
            text = post.title.toString(),
            style = MaterialTheme.typography.h6
        )

        val body = post.body.toString().replace("\n", " ")
        if (body.isEmpty()) return

        val bodyText = if (body.length > 120) {
            "${body.substring(120)}..."
        } else body

        Text(
            text = bodyText.trim(),
            style = MaterialTheme.typography.body1
        )
    }
}
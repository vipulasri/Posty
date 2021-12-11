package com.vipulasri.posty.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vipulasri.posty.R
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.ui.*

/**
 * Created by Vipul Asri on 09/12/21.
 */

@Composable
fun PostDetailsScreen(
    viewModel: PostDetailsVM,
    postId: String,
    onBack: () -> Unit
) {
    viewModel.getPostDetails(postId)
    val title = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title.value,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        }
    ) {
        when (val viewState = viewModel.viewState) {
            is PostDetailsViewState.Loading -> {
                Loading()
            }
            is PostDetailsViewState.Success -> {
                title.value = viewState.post.title
                PostDetails(post = viewState.post)
            }
            is PostDetailsViewState.Error -> {
                Error(message = viewState.message, onRetry = {
                    viewModel.getPostDetails(postId)
                })
            }
        }
    }
}

@Composable
private fun PostDetails(post: Post) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(DEFAULT_PADDING)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = post.body,
            style = MaterialTheme.typography.subtitle1
        )

        post.user?.let { user ->
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.posted_by),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = user.name,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = user.username,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}
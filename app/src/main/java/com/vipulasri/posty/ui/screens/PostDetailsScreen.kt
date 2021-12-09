package com.vipulasri.posty.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.vipulasri.posty.domain.model.Post

/**
 * Created by Vipul Asri on 09/12/21.
 */

@Composable
fun PostDetailsScreen(
    post: Post
) {
    Text(text = post.id)
}
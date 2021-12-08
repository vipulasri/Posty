package com.vipulasri.posty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vipulasri.posty.ui.PostsVM
import com.vipulasri.posty.ui.screens.PostListScreen
import com.vipulasri.posty.ui.theme.PostyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PostsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostyTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    }
                )
            }
        ) {
            Surface(color = MaterialTheme.colors.background) {
                PostListScreen(
                    viewState = viewModel.viewState,
                    onRetry = {
                        viewModel.getPosts()
                    }
                )
            }
        }
    }
}
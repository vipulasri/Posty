package com.vipulasri.posty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vipulasri.posty.ui.Navigation
import com.vipulasri.posty.ui.postdetails.PostDetailsScreen
import com.vipulasri.posty.ui.postdetails.PostDetailsVM
import com.vipulasri.posty.ui.postlist.PostListScreen
import com.vipulasri.posty.ui.postlist.PostsVM
import com.vipulasri.posty.ui.theme.PostyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
        val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }

        val navController = rememberNavController()
        NavHost(navController, startDestination = Navigation.PostListScreen.title) {
            composable(Navigation.PostListScreen.title) {
                val viewModel: PostsVM = viewModel(viewModelStoreOwner)
                PostListScreen(
                    viewModel,
                    navigateToDetails = { post ->
                        navController.navigate(Navigation.PostDetailScreen.title + "/${post.id}")
                    }
                )
            }
            composable(
                route = Navigation.PostDetailScreen.title + "/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("id") ?: ""
                val viewModel: PostDetailsVM = viewModel(viewModelStoreOwner)
                PostDetailsScreen(
                    viewModel,
                    postId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
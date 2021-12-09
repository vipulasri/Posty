package com.vipulasri.posty.ui

/**
 * Created by Vipul Asri on 09/12/21.
 */

sealed class Navigation(val title: String) {
    object PostListScreen : Navigation("post_list_screen")
    object PostDetailScreen : Navigation("post_details_screen")
}
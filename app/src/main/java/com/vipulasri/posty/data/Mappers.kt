package com.vipulasri.posty.data

import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.domain.model.Post

/**
 * Created by Vipul Asri on 09/12/21.
 */

fun GetPostsQuery.Data.toDomainModel(): List<Post> {
    return this.page?.posts
        ?.filterNotNull()
        ?.mapNotNull { post ->
            if (post.id != null && post.title != null && post.body != null) {
                Post(
                    post.id,
                    post.title,
                    post.body
                )
            } else null
        } ?: emptyList()
}
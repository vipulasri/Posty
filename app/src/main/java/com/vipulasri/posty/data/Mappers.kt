package com.vipulasri.posty.data

import com.vipulasri.posty.GetPostByIdQuery
import com.vipulasri.posty.GetPostsQuery
import com.vipulasri.posty.domain.model.Post
import com.vipulasri.posty.domain.model.User

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

fun GetPostByIdQuery.Data.toDomainModel(): Post {
    return this.post?.let { post ->
        Post(
            id = post.id ?: "",
            title = post.title ?: "",
            body = post.body ?: "",
            user = post.user.toDomainModel()
        )
    } ?: Post("", "", "")
}

private fun GetPostByIdQuery.User?.toDomainModel(): User {
    return this?.let { user ->
        User(
            id = user.id ?: "",
            name = user.name ?: "",
            username = user.username ?: ""
        )
    } ?: User("", "", "")
}
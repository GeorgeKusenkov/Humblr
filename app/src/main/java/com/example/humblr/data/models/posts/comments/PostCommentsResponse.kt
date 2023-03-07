package com.example.humblr.data.models.posts.comments

import com.example.humblr.data.models.posts.SubredditPostsResponse

class PostCommentsResponse(
    val subredditPostsResponse: SubredditPostsResponse,
    val commentsResponse: CommentsResponse
)
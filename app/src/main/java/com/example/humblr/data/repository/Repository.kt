package com.example.humblr.data.repository

import com.example.humblr.data.models.friends.FriendsResponse
import com.example.humblr.data.models.me.MeResponse
import com.example.humblr.data.models.posts.SubredditPostsResponse
import com.example.humblr.data.models.posts.comments.PostCommentsResponse
import com.example.humblr.data.models.saved.comments.SavedCommentsResponse
import com.example.humblr.data.models.saved.links.SavedLinksResponse
import com.example.humblr.data.models.user_info.UserInfoResponse


interface Repository {
    suspend fun getNewPosts(page:String): SubredditPostsResponse
    suspend fun getPopularPosts(page:String): SubredditPostsResponse
    suspend fun meInfo(): MeResponse
    suspend fun getFriends(): FriendsResponse
    suspend fun getSavedPosts(userName:String,page:String): SavedLinksResponse
    suspend fun getSavedComments(userName:String,page:String): SavedCommentsResponse
    suspend fun getPostComments(post:String): PostCommentsResponse
    suspend fun userInfo(userName:String): UserInfoResponse
}
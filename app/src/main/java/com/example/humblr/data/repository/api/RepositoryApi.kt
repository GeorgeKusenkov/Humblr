package com.example.humblr.data.repository.api

import com.example.humblr.data.models.friends.FriendsResponse
import com.example.humblr.data.models.me.MeResponse
import com.example.humblr.data.models.posts.SubredditPostsResponse
import com.example.humblr.data.models.posts.comments.PostCommentsResponse
import com.example.humblr.data.models.saved.comments.SavedCommentsResponse
import com.example.humblr.data.models.saved.links.SavedLinksResponse
import com.example.humblr.data.models.user_info.UserInfoResponse

interface RepositoryApi {
    suspend fun getSubreddits(count: Int,limit: Int, where: String)
    suspend fun getSubredditPosts(subreddit:String)
    suspend fun getNewPosts(page:String): SubredditPostsResponse
    suspend fun getPopularPosts(page:String): SubredditPostsResponse
    suspend fun getPostComments(post:String): PostCommentsResponse
    suspend fun searchSubreddits(count: Int,limit: Int,search:String)
    suspend fun meInfo(): MeResponse
    suspend fun userInfo(userName:String): UserInfoResponse
    suspend fun getFriends(): FriendsResponse
    suspend fun addToFriends(userName:String)
    suspend fun getSavedPosts(userName:String,page:String): SavedLinksResponse
    suspend fun getSavedComments(userName:String,page:String): SavedCommentsResponse
    suspend fun saveThing(category:String,id: String)
    suspend fun unsaveThing(id: String)
}
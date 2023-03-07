package com.example.humblr.data.repository

import android.util.Log
import com.example.humblr.data.models.friends.FriendsResponse
import com.example.humblr.data.models.me.MeResponse
import com.example.humblr.data.models.posts.SubredditPostsResponse
import com.example.humblr.data.models.posts.comments.PostCommentsResponse
import com.example.humblr.data.models.saved.comments.SavedCommentsResponse
import com.example.humblr.data.models.saved.links.SavedLinksResponse
import com.example.humblr.data.models.user_info.UserInfoResponse
import com.example.humblr.data.persistent_storage.PersistentStorage
import com.example.humblr.data.repository.api.RepositoryApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val repositoryApi: RepositoryApi,
    private val persistentStorage: PersistentStorage
):Repository {
    override suspend fun getNewPosts(page:String): SubredditPostsResponse {
        return repositoryApi.getNewPosts(page)
    }
    override suspend fun getPopularPosts(page:String): SubredditPostsResponse {
        Log.d("MyLog","Repository getPopularPosts")
        return repositoryApi.getPopularPosts(page)
    }
    override suspend fun meInfo(): MeResponse {
        return repositoryApi.meInfo()
    }
    override suspend fun getFriends(): FriendsResponse {
        return repositoryApi.getFriends()
    }
    override suspend fun getSavedPosts(userName:String, page:String): SavedLinksResponse {
        Log.d("SavedPosts","page:$page")
        return repositoryApi.getSavedPosts(userName,page)
    }
    override suspend fun getSavedComments(userName:String, page:String): SavedCommentsResponse {
        return repositoryApi.getSavedComments(userName,page)
    }
    override suspend fun getPostComments(post:String): PostCommentsResponse {
        return repositoryApi.getPostComments(post)
    }
    override suspend fun userInfo(userName:String): UserInfoResponse {
        return repositoryApi.userInfo(userName)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl):Repository
}
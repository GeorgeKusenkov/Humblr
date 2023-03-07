package com.example.humblr.presentation.fragment.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.repository.api.RepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestFragmentViewModel @Inject constructor(
    private val repositoryApi: RepositoryApi
):ViewModel() {
    fun getSubreddits(){
        viewModelScope.launch {
            repositoryApi.getSubreddits(0,2,"new")
        }
    }
    fun getSubredditPosts(){
        viewModelScope.launch {
            val subreddit = "Home"
            repositoryApi.getSubredditPosts(subreddit)
        }
    }
    fun getNewPosts(){
        viewModelScope.launch {
            repositoryApi.getNewPosts("")
        }
    }
    fun getPopularPosts(){
        viewModelScope.launch {
            repositoryApi.getPopularPosts("")
        }
    }
    fun getPostComments(){
        val post:String="10ee5sb"
        viewModelScope.launch {
            val response = repositoryApi.getPostComments(post)
            Log.d("MyLog",response.commentsResponse.toString())
        }
    }
    fun searchSubreddits(){
        viewModelScope.launch {
            repositoryApi.searchSubreddits(0,2,"dog")
        }
    }
    fun meInfo(){
        viewModelScope.launch {
            repositoryApi.meInfo()
        }
    }
    fun userInfo(){
        viewModelScope.launch {
            repositoryApi.userInfo("After_Solution1909")
        }
    }
    fun getFriends(){
        viewModelScope.launch {
            repositoryApi.getFriends()
        }
    }
    fun addToFriends(){
        viewModelScope.launch {
            val userName = "Quadaliacha"
            repositoryApi.addToFriends(userName)
        }
    }
    fun getSavedPosts(){
        val userName = "After_Solution1909"
        viewModelScope.launch {
            repositoryApi.getSavedPosts(userName,"")
        }
    }
    fun getSavedComments(){
        val userName = "After_Solution1909"
        viewModelScope.launch {
            repositoryApi.getSavedComments(userName,"")
        }
    }
    fun saveComment(){
        val id:String="ctu0ltr"
        val category:String = "comment"
        viewModelScope.launch {
            repositoryApi.saveThing(category,id)
        }
    }
    fun unsaveComment(){
        val id:String="ctu0ltr"
        viewModelScope.launch {
            repositoryApi.unsaveThing(id)
        }
    }
    fun savePost(){
        val id:String="3g1jfi"
        val category:String = "links"
        viewModelScope.launch {
            repositoryApi.saveThing(category,id)
        }
    }
    fun unsavePost(){
        val id:String="3g1jfi"
        viewModelScope.launch {
            repositoryApi.unsaveThing(id)
        }
    }
}
package com.example.humblr.presentation.fragment.detailedpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedPostFragmentViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    private val _postInfo = MutableStateFlow<com.example.humblr.data.models.posts.Post?>(null)
    val postInfo = _postInfo.asStateFlow()
    private val _comments = MutableStateFlow<List<com.example.humblr.data.models.posts.comments.Comment>?>(null)
    val comments = _comments.asStateFlow()
    fun getUserInfo(post:String){
        viewModelScope.launch {
            val info = repository.getPostComments(post)
            _postInfo.value = info.subredditPostsResponse.data?.posts?.first()
            val comments = info.commentsResponse.data?.children
            var allComments = mutableListOf<com.example.humblr.data.models.posts.comments.Comment>()
            var temp = mutableListOf<com.example.humblr.data.models.posts.comments.Comment>()
            comments?.let { comments ->
                temp.addAll(comments)
                //allComments.addAll(comments)
            }
            temp.forEach {
                allComments.addAll(getReplies(it))
            }
            _comments.value = allComments
        }

    }
    fun getReplies(comment: com.example.humblr.data.models.posts.comments.Comment):MutableList<com.example.humblr.data.models.posts.comments.Comment>{
        var allComments = mutableListOf<com.example.humblr.data.models.posts.comments.Comment>()
        allComments.add(comment)
        comment.data?.replies?.data?.children?.let { replies->
            if (replies.isNotEmpty())
            {
                replies.forEach { comment ->
                    allComments.addAll(getReplies(comment))
                }
            }
        }
        return allComments
    }
}
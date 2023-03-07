package com.example.humblr.data.repository.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.models.posts.Post
import com.example.humblr.data.repository.Repository
import javax.inject.Inject

class PostPagingSource @Inject constructor(
    private val repository: Repository
): PagingSource<String, Post>() {
    override fun getRefreshKey(state: PagingState<String, Post>): String?= FIRST_PAGE
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getNewPosts(page)
        }.fold(
            onSuccess = {
                Log.d("MyLog",it.toString())
                if (it.data?.posts?.size==0){
                    LoadResult.Page(
                        data = listOf<Post>(),
                        prevKey = null,
                        nextKey =  null
                    )
                }
                else{
                    it?.data?.posts?.let { posts ->
                            LoadResult.Page(
                                data = posts,
                                prevKey = null,
                                nextKey =  it.data?.after?:""
                            )
                    }
                }

            },
            onFailure = { LoadResult.Error(it) }
        )!!
    }
    companion object{
        private val FIRST_PAGE = ""
    }
}
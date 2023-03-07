package com.example.humblr.data.repository.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.models.saved.comments.SavedComment
import com.example.humblr.data.repository.Repository
import javax.inject.Inject

class SavedCommentPagingSource@Inject constructor(
    private val repository: Repository
): PagingSource<String, SavedComment>() {
    lateinit var username:String
    fun usernameInit(userName:String)
    {
        this.username = userName
    }
    override fun getRefreshKey(state: PagingState<String, SavedComment>): String?= FIRST_PAGE
    override suspend fun load(params: LoadParams<String>): LoadResult<String, SavedComment> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getSavedComments(username,page)
        }.fold(
            onSuccess = {
                Log.d("MyLog",it.toString())
                if (it.data?.children?.size!!< PAGE_SIZE){
                    it?.data?.children?.let { comments ->
                        LoadResult.Page(
                            data = comments,
                            prevKey = null,
                            nextKey =  null
                        )
                    }
                }
                else{
                    it?.data?.children?.let { posts ->
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
        const val PAGE_SIZE = 10
    }
}
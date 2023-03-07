package com.example.humblr.data.repository.paging_sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.humblr.data.models.saved.links.SavedLink
import com.example.humblr.data.repository.Repository
import javax.inject.Inject

class SavedPostPagingSource @Inject constructor(
    private val repository: Repository
): PagingSource<String, SavedLink>() {
    lateinit var username:String
    fun usernameInit(userName:String)
    {
        this.username = userName
    }
    override fun getRefreshKey(state: PagingState<String, SavedLink>): String?= FIRST_PAGE
    override suspend fun load(params: LoadParams<String>): LoadResult<String, SavedLink> {
        val page = params.key?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getSavedPosts(username,page)
        }.fold(
            onSuccess = {
                Log.d("MyLog",it.toString())
                if (it.data?.children?.size!!<PAGE_SIZE){
                    it?.data?.children?.let { posts ->
                        LoadResult.Page(
                            data = posts,
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
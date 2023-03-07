package com.example.humblr.presentation.fragment.subreddits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.humblr.R
import com.example.humblr.presentation.fragment.detailedpost.DetailedPostFragment
import com.example.humblr.presentation.fragment.user.UserFragment
import com.example.humblr.ui.compose.theming.CustomTheme
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class SubredditsFragment : Fragment() {
    private val viewModel: SubredditsFragmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadNew()
        return ComposeView(requireContext()).apply {
            setContent {
                CustomTheme() {
                    val mCheckedState = remember{ mutableStateOf(false)}
                    Scaffold(
                        topBar = {
                            SearchTopBar(
                                onSearchClicked = {
                                    /*findNavController().navigate(
                                        R.id.action_subredditsFragment_to_searchFragment
                                    )*/
                                }
                            )
                        },
                        content = {
                            Surface(modifier = Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxWidth()) {
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Row() {
                                        Text(text = "new")
                                        Switch(checked = mCheckedState.value, onCheckedChange = {
                                            mCheckedState.value = it
                                            if(it){viewModel.loadPopular()}
                                            else viewModel.loadNew()
                                        })
                                        Text(text = "popular")
                                    }
                                    PostsList(posts = viewModel.pagedPosts)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchTopBar(
        onSearchClicked: () -> Unit
    ){
        TopAppBar(
            title = {
                Text(
                    text = "search posts",
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        )
    }

    @Composable
    fun PostsList(posts: StateFlow<Flow<PagingData<com.example.humblr.data.models.posts.Post>>?>){
        val postsState = posts.collectAsState().value
        postsState?.let { postsState->
            val lazyPostItems: LazyPagingItems<com.example.humblr.data.models.posts.Post> = postsState.collectAsLazyPagingItems()
            LazyColumn{
                items(lazyPostItems){item->
                    item?.let {
                        PostScreen(post = it)
                    }
                }
            }
        }
    }

    @Composable
    fun PostScreen(post: com.example.humblr.data.models.posts.Post) {
        val textSizeCommon = 20.sp
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                val bundle = bundleOf()
                bundle.putString(DetailedPostFragment.POST_ID_KEY, post.data?.id!!)
                findNavController().navigate(
                    R.id.action_subredditsFragment_to_detailedPostFragment,
                    bundle
                )
            }) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Row(modifier = Modifier) {
                    post?.data?.title?.let {title->
                        Text(text = title, fontSize = textSizeCommon,modifier = Modifier.weight(1f))
                    }
                    IconButton(onClick = { Toast.makeText(context,"follow subreddit", Toast.LENGTH_LONG).show()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.vector__7),
                            contentDescription = stringResource(id = R.string.follow_post_description),
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }
                }
                post?.data?.url?.let {imgUrl->
                    GlideImage(imageModel = {imgUrl}, Modifier.requiredHeightIn(0.dp,200.dp),)
                }
                Row() {
                    post?.data?.author?.let { author->
                        TextButton(onClick = {
                            val bundle = bundleOf()
                            bundle.putString(UserFragment.USER_ID_KEY, author)
                            findNavController().navigate(
                                R.id.action_subredditsFragment_to_userFragment,
                                bundle
                            )
                        }) {
                            Text(text = author, fontSize = textSizeCommon)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    post?.data?.numComments?.let { numComments->
                        Text(text = numComments.toString(), fontSize = textSizeCommon)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.group_37),
//                        Icons.Default.Comment,
                        contentDescription = stringResource(id = R.string.comments_number),
                    )
                }
            }
        }
    }
}
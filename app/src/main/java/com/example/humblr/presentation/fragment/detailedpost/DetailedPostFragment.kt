package com.example.humblr.presentation.fragment.detailedpost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.humblr.R
import com.example.humblr.data.models.posts.comments.Comment
import com.example.humblr.presentation.fragment.user.UserFragment
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedPostFragment : Fragment() {
    private val viewModel:DetailedPostFragmentViewModel by viewModels()
    private var postId:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {postId = it.getString(POST_ID_KEY)  }
        Log.d("MyLog","postId: $postId")
        postId?.let { post->
            viewModel.getUserInfo(post)
        }
        return ComposeView(requireContext()).apply {
            setContent {
                Screen()
            }
        }
    }
    @Composable
    fun Screen() {
        Column {
            val post = viewModel.postInfo.collectAsState()
            post.value?.let { PostScreen(it) }
            val commentsList  = viewModel.comments.collectAsState()
            commentsList.value?.let{
                CommentsList(comments = commentsList.value)
            }
        }
    }
    @Composable
    fun PostScreen(post: com.example.humblr.data.models.posts.Post) {
        val textSizeCommon = 20.sp
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Row(modifier = Modifier) {
                    post?.data?.title?.let {title->
                        Text(text = title, fontSize = textSizeCommon,modifier = Modifier.weight(1f))
                    }
                    IconButton(onClick = { Toast.makeText(context,"followed", Toast.LENGTH_LONG).show() }) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    post?.data?.author?.let { author->
                        TextButton(onClick = {
                            val bundle = bundleOf()
                            bundle.putString(UserFragment.USER_ID_KEY, author)
                            findNavController().navigate(
                                R.id.action_detailedPostFragment_to_userFragment,
                                bundle
                            )
                        }) {
                            Text(text = author, fontSize = textSizeCommon)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    IconButton(onClick = {
                        ShareCompat.IntentBuilder(requireContext())
                            .setType("text/plain")
                            .setChooserTitle("Share URL")
                            .setText("${post.data?.url}")
                            .startChooser()
                    }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "share icon"
                        )
                    }
                    post?.data?.numComments?.let { numComments->
                        Text(text = numComments.toString(), fontSize = textSizeCommon)
                    }
                    Icon(
                        Icons.Default.Comment,
                        contentDescription = stringResource(id = R.string.comments_number),
                    )
                }
            }
        }
    }
    @Composable
    fun CommentsList(comments:List<com.example.humblr.data.models.posts.comments.Comment>?){
        comments?.let { commentsList->
            LazyColumn{
                items(commentsList){item->
                    item?.let {
                        if (it.kind!="more") {
                            CommentScreen(comment = it)
                            item.data?.replies?.data?.children.let {
                                Log.d("MyLog", it.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CommentScreen(comment: Comment) {
        val textSizeCommon = 20.sp
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Row(modifier = Modifier) {
                    comment?.data?.body?.let {body->
                        Text(text = body, fontSize = textSizeCommon,modifier = Modifier.weight(1f))
                    }

                }
                Row() {
                    IconButton(onClick = { Toast.makeText(context,"upvoted", Toast.LENGTH_LONG).show() }) {
                        Icon(
                            Icons.Default.ArrowUpward,
                            contentDescription = stringResource(id = R.string.follow_post_description),
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }
                    IconButton(onClick = { Toast.makeText(context,"downvoted", Toast.LENGTH_LONG).show() }) {
                        Icon(
                            Icons.Default.ArrowDownward,
                            contentDescription = stringResource(id = R.string.follow_post_description),
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = { Toast.makeText(context,"post saved", Toast.LENGTH_LONG).show() }) {
                        Icon(
                            Icons.Default.Save,
                            contentDescription = stringResource(id = R.string.follow_post_description),
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }
                    IconButton(onClick = { Toast.makeText(context,"downloaded", Toast.LENGTH_LONG).show() }) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = stringResource(id = R.string.follow_post_description),
                            modifier = Modifier
                                .size(36.dp)
                                .height(36.dp)
                                .width(36.dp)
                        )
                    }

                }
                Row() {
                    comment?.data?.author?.let { author->
                        TextButton(onClick = {
                            val bundle = bundleOf()
                            bundle.putString(UserFragment.USER_ID_KEY, author)
                            findNavController().navigate(
                                R.id.action_detailedPostFragment_to_userFragment,
                                bundle
                            )
                        }) {
                            Text(text = author, fontSize = textSizeCommon)
                        }

                    }
                }
            }
        }
    }
    companion object{
        const val POST_ID_KEY = "POST_ID_KEY"
    }
}
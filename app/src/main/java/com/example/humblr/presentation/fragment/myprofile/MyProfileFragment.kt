package com.example.humblr.presentation.fragment.myprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import com.example.humblr.R
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.humblr.presentation.fragment.user.UserFragment
import com.example.humblr.presentation.ui.compose.theming.CustomTheme
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class MyProfileFragment : Fragment() {
    private val viewModel:MyProfileFragmentViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUserInfo()
        return ComposeView(requireContext()).apply {
            setContent {
                CustomTheme() {
                    val openDialog = remember { mutableStateOf(false) }
                    Scaffold(
                        topBar = {
                            UserTopBar(onLogOutClicked = { openDialog.value = true })
                        }
                    ) {
                        val antiWarning = it
                        if (openDialog.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    openDialog.value = false
                                },
                                title = { Text(text = stringResource(id = R.string.action_confirmation)) },
                                text = { Text(stringResource(id = R.string.logout_confirmation)) },
                                confirmButton = {
                                    Button(
                                        //modifier = Modifier.weight(1f),
                                        onClick = {
                                            viewModel.logout()
                                            openDialog.value = false },
                                        content = {
                                            Text(stringResource(id = R.string.logout))
                                        }
                                    )
                                },
                                dismissButton = {
                                    Button(
                                        //modifier = Modifier.weight(1f),
                                        onClick = { openDialog.value = false }
                                    ) {
                                        Text(stringResource(id = R.string.cancel))
                                    }
                                }
                            )
                        }
                        Surface(modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxWidth()) {
                            screen(userInfo = viewModel.userInfo)
                        }
                    }
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserTopBar(
        onLogOutClicked: () -> Unit
    ){
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.user),
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = onLogOutClicked) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = stringResource(id = R.string.logout_icon_description)
                    )
                }
            }
        )
    }
    @Composable
    fun screen(userInfo: StateFlow<com.example.humblr.data.models.me.MeResponse?>){
        val privateInfoState = userInfo.collectAsState().value
        val friendsItems  = viewModel.friends.collectAsState()
        privateInfoState?.let {
            LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                item {
                    UserInfoScreen(it)
                }
                item {
                    Button(onClick = { Toast.makeText(context,"saved info cleared ", Toast.LENGTH_LONG).show() },Modifier.padding(top = 10.dp)) {
                        Text(text = stringResource(id = R.string.clear_saved))
                    }
                }
                friendsItems.value?.let { friendList ->
                    items(friendList) { friend ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            friend.name?.let { name ->
                                TextButton(onClick = {
                                    val bundle = bundleOf()
                                    bundle.putString(UserFragment.USER_ID_KEY, name)
                                    findNavController().navigate(
                                        R.id.action_myProfileFragment_to_userFragment,
                                        bundle
                                    )
                                }) {
                                    Text(
                                        text = name,
                                        modifier = Modifier.padding(10.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
    @Composable
    fun UserInfoScreen(userInfo: com.example.humblr.data.models.me.MeResponse){
        Column(horizontalAlignment = CenterHorizontally) {
            val textSizeCommon = 20.sp
            val textSizeUserName = 30.sp
            userInfo.snoovatarImg?.let { avatar->
                GlideImage(imageModel = {avatar}, modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 10.dp))
            }
            userInfo.name?.let { name->
                Text(text = "$name", fontSize = textSizeUserName,modifier = Modifier.align(CenterHorizontally), textAlign = TextAlign.Center)
            }
            userInfo.totalKarma?.let { karma->
                Text(text = "${stringResource(id = R.string.karma)}: ${karma.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(CenterHorizontally), textAlign = TextAlign.Center)
            }
            userInfo.isGold?.let { isGold ->
                Text(text = "${stringResource(id = R.string.isGold)}: ${isGold.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(CenterHorizontally), textAlign = TextAlign.Center)
            }
            userInfo.numFriends?.let { numFriends->
                Text(text = "${stringResource(id = R.string.friends)}: ${numFriends.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(CenterHorizontally), textAlign = TextAlign.Center)
            }
        }
    }

}
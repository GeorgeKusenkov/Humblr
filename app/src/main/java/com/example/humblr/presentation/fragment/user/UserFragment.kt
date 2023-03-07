package com.example.humblr.presentation.fragment.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.humblr.R
import androidx.fragment.app.viewModels
import com.example.humblr.data.models.user_info.UserInfo
import com.example.humblr.presentation.ui.compose.theming.CustomTheme
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var userId:String? = null
    private val viewModel:UserFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {userId = it.getString(USER_ID_KEY)  }
        Log.d("MyLog","userId: $userId")
        userId?.let { userId->
            viewModel.getUserInfo(userId)
        }
        return ComposeView(requireContext()).apply {
            setContent {
                CustomTheme() {
                    screen(userInfo = viewModel.userInfo)
                }
            }
        }
    }
    @Composable
    fun screen(userInfo: StateFlow<UserInfo?>){
        val privateInfoState = userInfo.collectAsState().value
        privateInfoState?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                UserInfoScreen(it)
            }
        }
    }
    @Composable
    fun UserInfoScreen(userInfo: UserInfo){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val textSizeCommon = 20.sp
            val textSizeUserName = 30.sp
            userInfo.snoovatarImg?.let { avatar->
                GlideImage(imageModel = {avatar}, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp))
            }
            userInfo.name?.let { name->
                Text(text = "$name", fontSize = textSizeUserName,modifier = Modifier.align(Alignment.CenterHorizontally), textAlign = TextAlign.Center)
            }
            userInfo.totalKarma?.let { karma->
                Text(text = "${stringResource(id = R.string.karma)}: ${karma.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ), textAlign = TextAlign.Center)
            }
            userInfo.isGold?.let { isGold ->
                Text(text = "${stringResource(id = R.string.isGold)}: ${isGold.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ), textAlign = TextAlign.Center)
            }
            userInfo.numFriends?.let { numFriends->
                Text(text = "${stringResource(id = R.string.friends)}: ${numFriends.toString()}", fontSize = textSizeCommon,modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ), textAlign = TextAlign.Center)
            }
            Button(onClick = { Toast.makeText(context,"added to friends", Toast.LENGTH_LONG).show()}) {
                Text(text = "add to friends", fontSize = textSizeUserName, textAlign = TextAlign.Center)
            }
        }
    }
    companion object{
        const val USER_ID_KEY = "USER_ID_KEY"
    }
}
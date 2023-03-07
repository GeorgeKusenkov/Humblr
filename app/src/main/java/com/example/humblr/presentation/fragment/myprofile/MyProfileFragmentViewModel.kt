package com.example.humblr.presentation.fragment.myprofile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.persistent_storage.PersistentStorage
import com.example.humblr.data.repository.Repository
import com.example.humblr.presentation.fragment.auth.AuthActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileFragmentViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository,
    private val persistentStorage:PersistentStorage
): ViewModel() {
    private val _userInfo = MutableStateFlow<com.example.humblr.data.models.me.MeResponse?>(null)
    val userInfo = _userInfo.asStateFlow()
    private val _friends = MutableStateFlow<List<com.example.humblr.data.models.friends.Friend>?>(null)
    val friends = _friends.asStateFlow()
    fun getUserInfo(){
        viewModelScope.launch {
            _userInfo.value = repository.meInfo()
            _friends.value = repository.getFriends().data?.friends
        }

    }
    fun logout(){
        viewModelScope.launch{
            persistentStorage.clear()
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent);
        }
    }
}
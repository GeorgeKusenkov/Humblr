package com.example.humblr.presentation.fragment.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.humblr.data.models.user_info.UserInfo
import com.example.humblr.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFragmentViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo = _userInfo.asStateFlow()
    fun getUserInfo(user: String){
        viewModelScope.launch {
            _userInfo.value = repository.userInfo(user).data
        }

    }
}
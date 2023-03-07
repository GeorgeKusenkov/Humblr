package com.example.humblr.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.humblr.domain.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
):ViewModel() {
    fun exchangeCodeForToken(uri: Uri) = getTokenUseCase.exchangeCodeForToken(uri)
}
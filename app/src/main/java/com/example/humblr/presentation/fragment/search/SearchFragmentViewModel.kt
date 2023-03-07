package com.example.humblr.presentation.fragment.search

import androidx.lifecycle.ViewModel
import com.example.humblr.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
}
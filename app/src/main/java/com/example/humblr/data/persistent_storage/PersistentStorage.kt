package com.example.humblr.data.persistent_storage

import com.example.humblr.data.models.user_info.UserInfo

interface PersistentStorage {
    fun addProperty(name: String?, value: String?)
    fun addProperty(name: String?, userInfo: UserInfo?)
    fun clear()
    fun getProperty(name: String?): String?
    companion object{
        const val STORAGE_NAME = "StorageName"
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val USER_INFO = "userinfo"
    }
}
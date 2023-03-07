package com.example.humblr.data.persistent_storage

import android.content.Context
import android.content.SharedPreferences
import com.example.humblr.data.models.user_info.UserInfo
import com.example.humblr.data.persistent_storage.utils.StorageConverter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersistentStorageImpl @Inject constructor(
    @ApplicationContext context: Context
):PersistentStorage {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var context: Context = context

    private fun init() {
        sharedPreferences = context.getSharedPreferences(PersistentStorage.STORAGE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    override fun addProperty(name: String?, value: String?) {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }
    override fun addProperty(name: String?, userInfo: UserInfo?) {
        if (sharedPreferences == null) {
            init()
        }
        val value = userInfo?.let {
            StorageConverter.userInfoToJson(it)
        }
        editor!!.putString(name, value)
        editor!!.apply()
    }
    override fun clear() {
        if (sharedPreferences == null) {
            init()
        }
        editor!!.clear()
        editor!!.apply()
    }

    override fun getProperty(name: String?): String? {
        if (sharedPreferences == null) {
            init()
        }
        return sharedPreferences!!.getString(name, null)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: PersistentStorageImpl):PersistentStorage
}
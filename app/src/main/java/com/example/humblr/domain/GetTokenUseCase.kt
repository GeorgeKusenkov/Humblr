package com.example.humblr.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.humblr.data.persistent_storage.PersistentStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.*
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val persistentStorage: PersistentStorage
) {
    fun exchangeCodeForToken(uri: Uri){
        var code = uri.toString().substringAfter("code=").substringBefore("#_")
        Log.d("MyLog",code)
        var authService = AuthorizationService(context)
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://www.reddit.com/api/v1/authorize"),  // authorization endpoint
            Uri.parse("https://www.reddit.com/api/v1/access_token")) // token endpoint
        val a = TokenRequest.Builder(serviceConfig, MY_CLIENT_ID)
            .setGrantType("authorization_code")
            .setAuthorizationCode(code)
            .setRedirectUri(MY_REDIRECT_URI.toUri())
            .build()
        Log.d("MyLog", "request: ${a.jsonSerializeString()}")
        val clientAuth: ClientAuthentication = ClientSecretBasic("")
        authService.performTokenRequest(
            TokenRequest.Builder(serviceConfig, MY_CLIENT_ID)
                .setGrantType("authorization_code")
                .setAuthorizationCode(code)
                .setRedirectUri(MY_REDIRECT_URI.toUri())
                .build(),
            clientAuth,
            AuthorizationService.TokenResponseCallback { resp, ex ->
                if (resp != null) {
                    Log.d(
                        "MyLog",
                        "accessToken:" + resp.accessToken.toString() + "\ntokenType: " + resp.tokenType + "\nscope: " + resp.scopeSet
                    )
                    persistentStorage.addProperty(PersistentStorage.AUTH_TOKEN,resp.accessToken.toString())
                    // exchange succeeded
                } else {
                    Log.d("MyLog", ex.toString())
                    // authorization failed, check ex for more details
                }
            })
    }
    /*fun exchangeCodeForToken(uri: Uri){

    }*/
    companion object{
        const val MY_CLIENT_ID:String ="pHb2ip75tYXA4koMx4ohsg"
        const val MY_REDIRECT_URI:String ="myproject://www.reddit.com/user/gKussenkov"
    }
}
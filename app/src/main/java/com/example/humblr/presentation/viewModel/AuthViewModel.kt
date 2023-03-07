package com.example.humblr.presentation.viewModel

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.humblr.MainActivity
import com.example.humblr.presentation.view.auth.AuthActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext context: Context,
) :ViewModel() {
    val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("https://www.reddit.com/api/v1/authorize"),  // authorization endpoint
        Uri.parse("https://www.reddit.com/api/v1/access_token")) // token endpoint
    var authState = AuthState(serviceConfig)
    var authRequestBuilder = AuthorizationRequest.Builder(
        serviceConfig,  // the authorization service configuration
        MY_CLIENT_ID,  // the client ID, typically pre-registered and static
        ResponseTypeValues.CODE,  // the response_type value: we want a code
        MY_REDIRECT_URI.toUri()
    ) // the redirect URI to which the auth response is sent
    var authRequest = authRequestBuilder
        .setScope("identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread")
        .build()
    var context = context
    var authService = AuthorizationService(context)
    /*fun rememberedAuth(){
        val token =persistantStorage.getProperty(PersistantStorage.AUTH_TOKEN)
        if (token!=null){
            Log.d("MyLog","token exist: $token")
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent);
        }
    }*/
    fun auth(){
        authService.performAuthorizationRequest(
            authRequest,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE),
            PendingIntent.getActivity(context, 0, Intent(context, AuthActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
        Log.d("MyLog","auth_button_click")

    }

    companion object{
        const val MY_CLIENT_ID:String ="pHb2ip75tYXA4koMx4ohsg"
        const val MY_REDIRECT_URI:String ="myproject://www.reddit.com/user/gKussenkov"
    }
}
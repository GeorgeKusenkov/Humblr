package com.example.humblr.presentation.view.auth

import androidx.annotation.DrawableRes
import com.example.humblr.R


sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.screen1,
        title = "Feed",
        description = "scroll post"
    )

    object Second : OnBoardingPage(
        image = R.drawable.screen2,
        title = "Comments",
        description = "look at comments,save, download, upvote, downvote them"
    )

    object Third : OnBoardingPage(
        image = R.drawable.screen3,
        title = "User",
        description = "open other user profiles, add them to friends"
    )
    object Fourth : OnBoardingPage(
        image = R.drawable.scren4,
        title = "Favourites",
        description = "add posts and comments to favourites"
    )
}
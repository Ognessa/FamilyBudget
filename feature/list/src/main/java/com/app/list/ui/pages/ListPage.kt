package com.app.list.ui.pages

import androidx.annotation.StringRes
import com.app.family_budget.list.R

enum class ListPage(
    @StringRes val title: Int
) {
    Trending(
        title = R.string.trending
    ),

    Favourites(
        title = R.string.favourites
    )
}
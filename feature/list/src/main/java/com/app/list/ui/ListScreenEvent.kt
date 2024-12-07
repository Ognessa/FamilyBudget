package com.app.list.ui

import com.app.list.model.RepoUi

sealed class ListScreenEvent {
    class OpenDetails(val repoUi: RepoUi) : ListScreenEvent()
}

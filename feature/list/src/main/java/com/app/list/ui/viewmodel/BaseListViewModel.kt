package com.app.list.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.list.model.RepoUi
import com.app.list.ui.ListScreenEvent
import com.app.list.ui.ListScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseListViewModel : ViewModel() {

    protected val _state = MutableStateFlow(ListScreenState())
    val state = _state.asStateFlow()

    protected val _event = MutableSharedFlow<ListScreenEvent>()
    val event: SharedFlow<ListScreenEvent> = _event.asSharedFlow()

    companion object {
        const val ITEMS_PER_PAGE = 20
        const val SORT = "stars"
        const val ORDER = "desc"
    }

    fun openRepoDetails(repoUi: RepoUi) {
        viewModelScope.launch {
            _event.emit(ListScreenEvent.OpenDetails(repoUi))
        }
    }
}
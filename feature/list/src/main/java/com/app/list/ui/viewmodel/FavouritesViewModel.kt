package com.app.list.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.app.family_budget.domain.Repository
import com.app.family_budget.domain.model.RepoDetailed
import com.app.list.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: Repository,
) : BaseListViewModel() {

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getFavourites().collect { list ->
                _state.update {
                    it.copy(
                        repoList = list.map(RepoDetailed::toUiModel),
                        isLoading = false,
                        hasNextPage = false,
                    )
                }
            }
        }
    }
}
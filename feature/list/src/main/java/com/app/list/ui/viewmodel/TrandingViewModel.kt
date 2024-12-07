package com.app.list.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.app.family_budget.domain.Repository
import com.app.family_budget.domain.common.AppError
import com.app.family_budget.domain.common.AppResult
import com.app.family_budget.domain.model.Filter
import com.app.family_budget.domain.model.RepoDetailed
import com.app.family_budget.domain.model.RepoListRequestParams
import com.app.list.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: Repository,
) : BaseListViewModel() {

    fun onSearchQueryChange(searchQuery: String) {
        _state.update { it.copy(searchQuery = searchQuery) }
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = repository.getTrending(
                RepoListRequestParams(
                    searchQuery = state.value.searchQuery,
                    perPage = ITEMS_PER_PAGE,
                    page = 1,
                    sort = SORT,
                    order = ORDER,
                    filter = state.value.currentFilter,
                )
            )

            when (result) {
                is AppResult.Success -> _state.update {
                    it.copy(
                        repoList = result.data.map(RepoDetailed::toUiModel),
                        isLoading = false,
                        currentPage = 1,
                        hasNextPage = result.data.size >= ITEMS_PER_PAGE,
                        error = AppError.None(),
                    )
                }

                is AppResult.Error -> _state.update {
                    it.copy(
                        error = result.error,
                        isLoading = false,
                        currentPage = 1
                    )
                }
            }
        }
    }

    fun loadNextPage() {
        if (state.value.loadingNextPage) return

        _state.update { it.copy(loadingNextPage = true) }

        viewModelScope.launch {
            val result = repository.getTrending(
                RepoListRequestParams(
                    searchQuery = state.value.searchQuery,
                    perPage = ITEMS_PER_PAGE,
                    page = state.value.currentPage + 1,
                    sort = SORT,
                    order = ORDER,
                    filter = state.value.currentFilter,
                )
            )

            when (result) {
                is AppResult.Success -> _state.update {
                    it.copy(
                        repoList = state.value.repoList + result.data.map(RepoDetailed::toUiModel),
                        loadingNextPage = false,
                        currentPage = state.value.currentPage + 1,
                        hasNextPage = result.data.size >= ITEMS_PER_PAGE,
                        error = AppError.None(),
                    )
                }

                is AppResult.Error -> _state.update {
                    it.copy(
                        error = result.error,
                        loadingNextPage = false,
                    )
                }
            }
        }
    }

    fun updateList() {
        _state.update { it.copy(isLoading = true) }
        loadData()
    }

    fun onFilterSelect(filter: Filter) {
        _state.update { it.copy(currentFilter = filter) }
        updateList()
    }
}
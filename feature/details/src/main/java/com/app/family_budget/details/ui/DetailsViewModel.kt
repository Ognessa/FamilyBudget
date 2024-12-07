package com.app.family_budget.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.family_budget.details.model.toDomainModel
import com.app.family_budget.details.model.toUiModel
import com.app.family_budget.domain.Repository
import com.app.family_budget.domain.common.AppResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted private val repoId: Long,
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state = _state.asStateFlow()

    init {
        loadRepo()
    }

    private fun loadRepo() {
        viewModelScope.launch {
            val result = repository.getById(repoId)
            val isFavourite = repository.isFavourite(repoId)

            when (result) {
                is AppResult.Success -> _state.update { it.copy(repoUi = result.data?.toUiModel(isFavourite)) }
                is AppResult.Error -> _state.update { it.copy(error = result.error) }
            }
        }
    }

    fun onFavouriteClick() {
        viewModelScope.launch {
            if (state.value.repoUi?.isFavourite == true) {
                repository.deleteFromFavourite(repoId)
            } else {
                repository.addToFavourites(state.value.repoUi?.toDomainModel() ?: return@launch)
            }

            val isFavourite = repository.isFavourite(repoId)

            _state.update { it.copy(repoUi = state.value.repoUi?.copy(isFavourite = isFavourite)) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(repoId: Long): DetailsViewModel
    }
}
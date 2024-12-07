package com.app.list.ui.content

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.family_budget.domain.common.AppError
import com.app.family_budget.domain.model.Filter
import com.app.family_budget.list.R
import com.app.list.model.RepoUi
import com.app.list.ui.ListScreenState
import com.app.ui.theme.ComposeBoilerplateTheme
import java.time.LocalDateTime
import com.app.family_budget.core.ui.R as CoreR

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ListScreenContent(
    listScreenState: ListScreenState,
    modifier: Modifier = Modifier,
    currentFilter: Filter? = null,
    filters: List<Filter> = listOf(),
    onSearchQueryChange: (String) -> Unit = { },
    onListItemClick: (RepoUi) -> Unit = { },
    onUpdateList: () -> Unit = { },
    loadNextPage: () -> Unit = { },
    onFilterSelect: (Filter) -> Unit = { },
    showSearchBar: Boolean = false,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = listScreenState.isLoading,
        onRefresh = onUpdateList,
        refreshingOffset = 50.dp,
        refreshThreshold = 50.dp
    )

    val filterButton = @Composable {
        if (currentFilter != null) FilterButton(currentFilter, filters, onFilterSelect)
    }

    val lazyListState = rememberLazyListState()

    setupPaging(lazyListState, listScreenState, loadNextPage)

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (listScreenState.error !is AppError.None) {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val message = when (listScreenState.error) {
                    is AppError.ApiLimit -> stringResource(id = CoreR.string.api_request_limit_error)
                    is AppError.Http -> stringResource(
                        id = CoreR.string.http_error_code,
                        listScreenState.error.code
                    )

                    is AppError.Unknown -> stringResource(id = CoreR.string.unknown_error_message)
                    is AppError.None -> ""
                }

                Text(text = message, modifier = Modifier.padding(8.dp))
            }
        }

        if (showSearchBar) {
            SearchBar(
                listScreenState = listScreenState,
                onSearchQueryChange = onSearchQueryChange,
                currentFilter = currentFilter,
                filterButton = filterButton,
                onSearchClick = onUpdateList
            )
        }

        Box(
            modifier = modifier
                .pullRefresh(pullRefreshState)
                .weight(1f),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
            ) {
                itemsIndexed(
                    items = listScreenState.repoList,
                    key = { _: Int, item: RepoUi -> item.id }
                ) { index: Int, item: RepoUi ->
                    ListItem(
                        repoUi = item,
                        onItemClick = onListItemClick,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }

                if (listScreenState.loadingNextPage && listScreenState.isLoading.not()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            if (listScreenState.repoList.isEmpty() && listScreenState.isLoading.not()) {
                Text(
                    text = stringResource(R.string.no_items_found),
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            PullRefreshIndicator(
                refreshing = listScreenState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun setupPaging(
    lazyListState: LazyListState,
    listScreenState: ListScreenState,
    onLoadNextPage: () -> Unit,
    threshold: Int = 3
) {
    val shouldLoadNextPage by remember {
        derivedStateOf {
            val lastIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: (threshold - 1)
            val totalItems = lazyListState.layoutInfo.totalItemsCount - threshold

            listScreenState.hasNextPage
                    && lastIndex >= totalItems
                    && listScreenState.loadingNextPage.not()
        }
    }

    LaunchedEffect(shouldLoadNextPage) {
        if (shouldLoadNextPage) {
            Log.d(
                "SSSS",
                "lastIndex ${lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: (-threshold - 1)} - " +
                        "totalItems ${lazyListState.layoutInfo.totalItemsCount - threshold}"
            )

            onLoadNextPage()
        }
    }
}

@Composable
private fun SearchBar(
    listScreenState: ListScreenState,
    onSearchQueryChange: (String) -> Unit,
    currentFilter: Filter?,
    filterButton: @Composable () -> Unit,
    onSearchClick: () -> Unit
) {
    OutlinedTextField(
        value = listScreenState.searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        trailingIcon = if (currentFilter == null) {
            null
        } else {
            filterButton
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearchClick()
        })
    )
}

@Composable
private fun FilterButton(
    currentFilter: Filter,
    filters: List<Filter> = listOf(),
    onFilterSelect: (Filter) -> Unit = { },
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(id = currentFilter.filterNameRes),
            modifier = Modifier.clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = filter.filterNameRes)) },
                    onClick = {
                        expanded = false
                        onFilterSelect(filter)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListContentPreview() {
    ComposeBoilerplateTheme {
        val list = List(5) {
            RepoUi(
                id = it.toLong(),
                repoImageUrl = "https://secure.gravatar.com/avatar/e806b212e0f7aaebd313a10f8be6466e?s=800&d=identicon",
                username = "Harley",
                repoName = "Maxine",
                description = "Aubree AubreeAubree Aubree Aubree " +
                        "AubreeAubreeAubree Aubree Aubree",
                starsCount = 123,
                repoUrl = "Britanny",
                createdAt = LocalDateTime.now(),
                forksCount = 9449L,
                language = null,
            )
        }

        val state =
            ListScreenState(repoList = listOf(), error = AppError.ApiLimit(), isLoading = false)

        ListScreenContent(
            state,
            showSearchBar = true,
            filters = Filter.entries.toList(),
            currentFilter = Filter.LastMonth
        )
    }
}
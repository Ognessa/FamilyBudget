package com.app.list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.family_budget.domain.model.Filter
import com.app.list.model.RepoUi
import com.app.list.ui.content.ListScreenContent
import com.app.list.ui.content.PagesBottomBar
import com.app.list.ui.pages.ListPage
import com.app.list.ui.viewmodel.FavouritesViewModel
import com.app.list.ui.viewmodel.TrendingViewModel
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ListScreen(
    trendingViewModel: TrendingViewModel = hiltViewModel(),
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    openDetailsScreen: (RepoUi) -> Unit,
) {

    LaunchedEffect(Unit) {
        merge(trendingViewModel.event, favouritesViewModel.event).collect {
            when (it) {
                is ListScreenEvent.OpenDetails -> openDetailsScreen(it.repoUi)
            }
        }
    }

    val pagerState = rememberPagerState(pageCount = { ListPage.entries.size })

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            PagesBottomBar(
                pagerState = pagerState,
                onScreenSelect = { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(ListPage.entries.indexOf(page))
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(it),
        ) { pageNumber ->

            when (ListPage.entries[pageNumber]) {
                ListPage.Trending -> {
                    val state by trendingViewModel.state.collectAsStateWithLifecycle()

                    ListScreenContent(
                        listScreenState = state,
                        onSearchQueryChange = trendingViewModel::onSearchQueryChange,
                        onListItemClick = trendingViewModel::openRepoDetails,
                        showSearchBar = true,
                        onUpdateList = trendingViewModel::updateList,
                        currentFilter = state.currentFilter,
                        filters = Filter.entries.toList(),
                        onFilterSelect = trendingViewModel::onFilterSelect,
                        loadNextPage = trendingViewModel::loadNextPage
                    )
                }

                ListPage.Favourites -> {
                    val state by favouritesViewModel.state.collectAsStateWithLifecycle()

                    ListScreenContent(
                        listScreenState = state,
                        onListItemClick = favouritesViewModel::openRepoDetails,
                    )
                }
            }
        }
    }
}
package com.app.list.ui.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.list.ui.pages.ListPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagesBottomBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onScreenSelect: (ListPage) -> Unit
) {
    Surface(
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ListPage.entries.forEach { page ->
                BottomBarItem(
                    listPage = page,
                    isSelected = page.ordinal == pagerState.currentPage,
                    onClick = { onScreenSelect(page) }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    listPage: ListPage,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(id = listPage.title).uppercase(),
                    color = if(isSelected)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    )
}
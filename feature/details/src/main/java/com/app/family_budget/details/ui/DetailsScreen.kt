package com.app.family_budget.details.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.family_budget.details.R
import com.app.family_budget.details.model.RepoUi
import com.app.family_budget.domain.common.AppError
import com.app.ui.theme.ComposeBoilerplateTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.app.family_budget.core.ui.R as CoreR

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailsScreenContent(
        repoUi = state.repoUi ?: return,
        error = state.error,
        onFavouriteClick = viewModel::onFavouriteClick,
    )
}

@Composable
internal fun DetailsScreenContent(
    repoUi: RepoUi,
    error: AppError,
    modifier: Modifier = Modifier,
    onFavouriteClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (error !is AppError.None) {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val message = when (error) {
                    is AppError.ApiLimit -> stringResource(id = CoreR.string.api_request_limit_error)
                    is AppError.Http -> stringResource(
                        id = CoreR.string.http_error_code,
                        error.code
                    )

                    is AppError.Unknown -> stringResource(id = CoreR.string.unknown_error_message)
                    is AppError.None -> ""
                }

                Text(text = message, modifier = Modifier.padding(8.dp))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        painter = painterResource(id = CoreR.drawable.star),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(text = "${repoUi.starsCount}", style = MaterialTheme.typography.bodyMedium)
                }

                AsyncImage(
                    model = repoUi.repoImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopCenter),
                    placeholder = painterResource(
                        id = CoreR.drawable.placeholder
                    )
                )

                IconButton(
                    onClick = onFavouriteClick,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    val iconRes = if (repoUi.isFavourite) {
                        CoreR.drawable.ic_heart_filled
                    } else {
                        CoreR.drawable.ic_heart_outlined
                    }

                    Icon(painter = painterResource(id = iconRes), contentDescription = null)
                }
            }

            Text(
                text = repoUi.username,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            val dateTime = remember {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                repoUi.createdAt.format(formatter)
            }

            Text(text = repoUi.repoName, style = MaterialTheme.typography.titleMedium)

            DetailsField(stringResource(R.string.created_at), dateTime)

            if (repoUi.language != null) {
                DetailsField(stringResource(R.string.language), repoUi.language)
            }

            DetailsField(stringResource(R.string.number_of_forks), "${repoUi.forksCount}")

            val context = LocalContext.current

            Text(
                text = repoUi.repoUrl,
                textDecoration = TextDecoration.Underline,
                color = Color.Blue,
                modifier = Modifier.clickable {
                    openUrlInBrowser(context, repoUi.repoUrl)
                })

            Text(text = repoUi.description)
        }
    }
}

@Composable
private fun DetailsField(text: String, value: String) {
    Row {
        Text(text = text)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value, modifier = Modifier.weight(1f))
    }
}

private fun openUrlInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(context, intent, Bundle())
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    ComposeBoilerplateTheme {
        DetailsScreenContent(
            repoUi = RepoUi(
                id = 524L,
                starsCount = 5315,
                repoName = "Edmundo",
                username = "Deandrea",
                repoImageUrl = "Zeth",
                description = "Naisha",
                repoUrl = "Zachary",
                createdAt = LocalDateTime.now(),
                forksCount = 5746L,
                language = null,
                isFavourite = true
            ),
            error = AppError.ApiLimit()
        )
    }
}
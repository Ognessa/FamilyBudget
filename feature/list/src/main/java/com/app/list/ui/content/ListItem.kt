package com.app.list.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.family_budget.core.ui.R
import com.app.list.model.RepoUi
import com.app.ui.theme.ComposeBoilerplateTheme
import java.time.LocalDateTime

@Composable
internal fun ListItem(
    repoUi: RepoUi,
    modifier: Modifier = Modifier,
    onItemClick: (RepoUi) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(repoUi) }
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = repoUi.repoImageUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            placeholder = painterResource(
                id = R.drawable.placeholder
            ),
            error = painterResource(
                id = R.drawable.placeholder
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = repoUi.username, modifier = Modifier.weight(1f))
                Text(text = "${repoUi.starsCount}", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(text = repoUi.repoName, style = MaterialTheme.typography.titleMedium)
            Text(
                text = repoUi.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemPreview() {
    ComposeBoilerplateTheme {
        ListItem(
            RepoUi(
                id = 1,
                repoImageUrl = "https://secure.gravatar.com/avatar/e806b212e0f7aaebd313a10f8be6466e?s=800&d=identicon",
                username = "Harley",
                repoName = "Maxine",
                description = "Aubree AubreeAubree Aubree Aubree " +
                        "AubreeAubreeAubree Aubree Aubree",
                starsCount = 123,
                repoUrl = "Jerrel",
                createdAt = LocalDateTime.now(),
                forksCount = 5203L,
                language = null,
            )
        ) {}
    }
}
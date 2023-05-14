package com.example.githubsub.ui.screen.issuelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.githubsub.ui.screen.ObserveLifecycleEvent
import com.example.githubsub.ui.theme.GithubSubTheme

@Composable
@Preview
fun IssueList(
    viewModel: IssueListViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    ObserveLifecycleEvent { event ->
        // 検出したイベントに応じた処理を実装する。
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel.fetchIssue()
            }
            else -> {}
        }
    }

    val focusRequester = remember { FocusRequester() }
    val state by viewModel.state.collectAsState()
    GithubSubTheme {
        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { focusRequester.requestFocus() },
                    enabled = true,
                )
                .focusRequester(focusRequester)
                .focusTarget()
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = "Issues") }
            )
            LazyColumn {
                items(state.searchedIssue.items) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {  },
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 8.dp
                    ) {

                        Column(
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {

                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.subtitle1
                            )
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "リポジトリ名",
                                    style = MaterialTheme.typography.body2
                                )
                                Text(
                                    text = "ユーザー名",
                                    style = MaterialTheme.typography.body2
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}


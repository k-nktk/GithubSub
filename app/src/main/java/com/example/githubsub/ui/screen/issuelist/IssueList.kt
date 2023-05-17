package com.example.githubsub.ui.screen.issuelist

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.example.githubsub.ui.screen.ObserveLifecycleEvent
import com.example.githubsub.ui.theme.GithubSubTheme

@Composable
@Preview
fun IssueList(
    viewModel: IssueListViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClickForNav: () -> Unit = {}
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
//                items(state.searchedContent.)
                items(state.issueListContent) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .clickable(onClick = onClickForNav),
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 8.dp
                    ) {

                        Row() {
                            AsyncImage(
                                model = it.avatarUrl,
                                contentDescription = null,
                                Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                            )
                            Column(
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Row() {
                                    Text(
                                        text = it.user,
                                        style = MaterialTheme.typography.body2,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "・${it.repositoryTitle}",
                                        style = MaterialTheme.typography.body2,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = it.issueTitle,
                                    style = MaterialTheme.typography.subtitle1
                                )
                                Row(
                                    Modifier
                                        .height(16.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row() {
                                        viewModel.provideLabelColor(it.labels).map {
                                            Canvas(
                                                modifier = Modifier
                                                    .padding(horizontal = 1.dp)
                                                    .size(16.dp),
                                            ) {
                                                if (it == Color(0xffffffff)) {
                                                    drawCircle(
                                                        color = Color.Black,
                                                        style = Stroke(width = 1.0f)
                                                    )
                                                } else {
                                                    drawCircle(
                                                        color = it
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


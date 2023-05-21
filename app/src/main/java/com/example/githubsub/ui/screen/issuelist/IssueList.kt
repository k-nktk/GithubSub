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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.example.githubsub.model.Label
import com.example.githubsub.ui.theme.GithubSubTheme

@Composable
fun IssueList(
    viewModel: IssueListViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClickForNav: (String, String, Int, String, String, List<Label>) -> Unit = {
            owner: String, repo: String, issueNumber: Int, ownerImageUrl: String, issueTitle: String, issueLabel: List<Label> -> },
) {
//    ObserveLifecycleEvent { event ->
//        // 検出したイベントに応じた処理を実装する。
//        when (event) {
//            Lifecycle.Event.ON_CREATE -> {
//                viewModel.fetchIssue()
//            }
//            else -> {}
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.fetchIssue()
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
            if (state.issueListContent.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        "現在Issueはありません",
                        style = MaterialTheme.typography.h4
                    )
                }

            } else {
                LazyColumn {
                    items(state.issueListContent) {
                        Card(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .clickable(onClick = {
                                    Log.d(
                                        "onclicktest",
                                        "${it.user}/${it.repositoryTitle}/${it.issueNumber}"
                                    )
                                    onClickForNav(
                                        it.user,
                                        it.repositoryTitle,
                                        it.issueNumber,
                                        it.avatarUrl,
                                        it.issueTitle,
                                        it.labels
                                    )
                                }),
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
                                        .padding(4.dp)
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
}


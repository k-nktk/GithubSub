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
    viewModel: BaseIssueListViewModel = hiltViewModel<IssueListViewModel>(),
    onClickForNav: (String, String, Int, String, String, List<Label>) -> Unit = {
            owner: String, repo: String, issueNumber: Int, ownerImageUrl: String, issueTitle: String, issueLabel: List<Label> -> },
) {
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
            if (state.issueList.isEmpty()) {
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
                    items(state.issueList) {
                        Card(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .clickable(onClick = {
                                    Log.d(
                                        "onclicktest",
                                        "${it.user}/${it.projectTitle}/${it.issueNumber}"
                                    )
                                    onClickForNav(
                                        it.user,
                                        it.projectTitle,
                                        it.issueNumber,
                                        it.imageUrl,
                                        it.issueTitle,
                                        it.labelList
                                    )
                                }),
                            backgroundColor = MaterialTheme.colors.background,
                            elevation = 8.dp
                        ) {

                            Row(
                                Modifier.padding(8.dp)
                            ) {
                                AsyncImage(
                                    model = it.imageUrl,
                                    contentDescription = null,
                                    Modifier
                                        .clip(CircleShape)
                                        .size(40.dp)
                                )
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row() {
                                        Text(
                                            text = it.user,
                                            style = MaterialTheme.typography.body2,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "・${it.projectTitle}",
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
                                            viewModel.provideLabelColor(it.labelList).map {
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

@Composable
@Preview
fun PreviewIssueList() {
    IssueList(viewModel = PreviewIssueListViewModel())
}


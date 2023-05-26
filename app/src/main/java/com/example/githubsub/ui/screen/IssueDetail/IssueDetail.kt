package com.example.githubsub.ui.screen.IssueDetail

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.githubsub.model.Label
import com.example.githubsub.ui.theme.GithubSubTheme
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun IssueDetail(
    viewModel: BaseIssueDetailViewModel = hiltViewModel<IssueDetailViewModel>(),
    onClickForNav: () -> Unit = {},
    owner: String,
    repo: String,
    issueNumber: Int,
    ownerImageUrl: String,
    issueTitle: String,
    issueLabel: List<Label>
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        Log.d("onclick", "${owner}/${repo}/${issueNumber}")

        viewModel.getIssueDetail(owner, repo, issueNumber)
    }

    GithubSubTheme {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(text = issueTitle) },
                navigationIcon = {
                    IconButton(onClick = onClickForNav) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = issueTitle, style = MaterialTheme.typography.h6)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = ownerImageUrl,
                            contentDescription = null,
                            Modifier
                                .clip(CircleShape)
                                .size(32.dp)
                                .padding(4.dp)
                                .clickable { }
                        )


                        Text("$owner opened this issue")
                    }
//                    LazyHorizontalGrid(rows = GridCells.Adaptive(0.dp), content = )
                    FlowRow(
                        mainAxisAlignment = FlowMainAxisAlignment.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        issueLabel.map{
                            Row(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .size(16.dp),
                                ) {
                                    val labelColor = viewModel.provideLabelColor(it)
                                    drawCircle(
                                        color = labelColor
                                    )
//                                    }
                                }
                                Text(
                                    text = it.name,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )

                            }
                        }
                    }
                }
            }
            LazyColumn {
                items(state.commentItems) {
                    Row(
                        Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            model = it.user.imageUrl,
                            contentDescription = null,
                            Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                        )
                        Column(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = it.user.login,
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = it.comment,
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

@Preview("test screen")
@Composable
fun PreviewTestScreen() {
//    TestScreen(user = User(id = "1", name = "tom"), count = 10)
    IssueDetail(
        viewModel = PreviewIssueDetailViewModel(),
        owner = "NakatsukaKyohei",
        repo = "GithubSub",
        issueNumber = 2,
        ownerImageUrl = "https://avatars.overconscientious.com/u/44229263?v=4",
        issueTitle = "TestIssueTitle",
        issueLabel = mutableListOf(Label(id = 0, name = "bug", color = "ffffff"), Label(id = 0, name = "enhancement", color = "ffffff"), Label(id = 0, name = "enhancement", color = "ffffff"), Label(id = 0, name = "enhancement", color = "ffffff"))
    )
}

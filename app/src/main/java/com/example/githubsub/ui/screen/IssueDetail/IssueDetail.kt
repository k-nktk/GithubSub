package com.example.githubsub.ui.screen.IssueDetail

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.githubsub.ui.screen.ObserveLifecycleEvent
import com.example.githubsub.ui.theme.GithubSubTheme
import javax.inject.Inject

@Composable
fun IssueDetail(
    viewModel: IssueDetailViewModel = hiltViewModel(),
    onClickForNav: () -> Unit = {},
    owner: String,
    repo: String,
    issueNumber: Int
) {
//    ObserveLifecycleEvent { event ->
//        // 検出したイベントに応じた処理を実装する。
//        when (event) {
//            Lifecycle.Event.ON_CREATE -> {
//                viewModel.getIssueDetail()
//            }
//            else -> {}
//        }
//    }

    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        Log.d("onclicktest", "${owner}/${repo}/${issueNumber}")

        viewModel.getIssueDetail(owner, repo, issueNumber)
    }

    GithubSubTheme {
        Column() {
            TopAppBar(
                title = { Text(text = state.issueTitle) },
                navigationIcon = {
                    IconButton(onClick = onClickForNav) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
            LazyColumn {
                items(state.commentItems) {
                    Row() {
                        AsyncImage(model = it.user.imageUrl, contentDescription = null)
                        Column() {
                            Text(text = it.user.login)
                            Text(text = it.comment)
                        }
                    }
                }
            }
        }

    }
}
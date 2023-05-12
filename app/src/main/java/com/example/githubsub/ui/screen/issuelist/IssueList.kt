package com.example.githubsub.ui.screen.issuelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.githubsub.ui.theme.GithubSubTheme

@Composable
@Preview
fun IssueList(
    viewModel: IssueListViewModel = hiltViewModel()
) {
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
        Row(
            Modifier
                .fillMaxWidth()
//                .height(60.dp)
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,

        ) {
            OutlinedTextField(
                singleLine = true,
                value = state.query,
                placeholder = { Text(text = "Github Username") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.searchIssue() }
                ),
                onValueChange = {
                    viewModel.setQuery(it)
                },
//                modifier = Modifier
//                    .graphicsLayer {
//                        this.alpha = 1.0f
//                    }
//                    .padding(start = 16.dp, end = 16.dp)
            )
        }

            LazyColumn {
                items(state.searchedIssue.items) {
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 8.dp
                    ) {
                        Row(
                            Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {}
                                )
                                .padding(4.dp)
                        ) {
//                    AsyncImage(
//                        model = it.imageUrl,
//                        contentDescription = null,
//                    )
                            Text(text = it.title)
                        }
                    }
                }
            }
    }

    }
}
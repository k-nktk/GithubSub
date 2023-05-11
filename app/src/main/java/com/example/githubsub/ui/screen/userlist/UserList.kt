package com.example.githubsub.ui.screen.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
@Preview
fun UserList(
    viewModel: UserListViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val state by viewModel.state.collectAsState()

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
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                singleLine = true,
                value = state.query,
                label = { Text(text = "Github Username") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.searchUser() }
                ),
                onValueChange = {
                    viewModel.setQuery(it)
                }
            )
        }

        LazyColumn {
            items(state.searchedUser.items) {
                Row(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .clickable(
                        onClick = {}
                        )
                ) {
                    AsyncImage(
                        model = it.imageUrl,
                        contentDescription = null,
                    )
                    Text(text = it.name)
                }
            }
        }
    }
}
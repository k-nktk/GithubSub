package com.example.githubsub.ui.screen.repositorylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RepositoryList(
    viewModel: BaseRepositoryListViewModel = hiltViewModel<RepositoryListViewModel>()
) {

    val state by viewModel.state.collectAsState()
    Column {
        TopAppBar(
            title = { Text(text = "Repository") }
        )
        TextField(
            value = state.query,
            onValueChange = {
                viewModel.setQuery(it)
                viewModel.searchRepository()
            }
        )
        LazyColumn {
            items(state.searchResponse.items) {
                Text(text = it.fullName)
            }
        }
    }
}

@Composable
@Preview
fun PreviewRepositoryList() {
    RepositoryList(
        viewModel = PreviewRepositoryListViewModel()
    )
}
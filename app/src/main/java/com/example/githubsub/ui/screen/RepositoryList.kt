package com.example.githubsub.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubsub.model.SearchItem
import com.example.githubsub.model.SearchResponse

@Composable
@Preview
fun RepositoryList(
    viewModel: RepositoryListViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    Column {
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
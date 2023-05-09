package com.example.githubsub.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubsub.model.SearchItem
import com.example.githubsub.model.SearchResponse

@Composable
fun RepositoryList(
    viewModel: RepositoryListViewModel = hiltViewModel()
) {
    lateinit var searchResponse: List<SearchItem>
//    val lifecycleOwner = LocalLifecycleOwner.current

    val textState = remember { mutableStateOf(TextFieldValue()) }

    viewModel.searchResult.observeForever { result ->
        if (result != null){
            result.onSuccess {
                searchResponse = it.contents
            }
            result.onFailure {
                Log.d("Search", it.toString())
            }
        } else {
            searchResponse = mutableListOf()
        }
    }
    Column {
        TextField(
            value = textState.value,
            onValueChange = { it ->
                textState.value = it
                viewModel.searchRepository(it.toString())
            }
        )
        LazyColumn {
            items(searchResponse) { it ->
                Text(text = it.fullName)
            }
        }
    }
}
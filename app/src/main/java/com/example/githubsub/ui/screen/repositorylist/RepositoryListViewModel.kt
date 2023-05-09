package com.example.githubsub.ui.screen.repositorylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.model.SearchedRepository
import com.example.githubsub.repository.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(): ViewModel(){

    private val _state = MutableStateFlow(RepositoryListState.initValue)
    val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> RepositoryListState) {
        _state.value = newState()
    }

    //API
    //Todo: Inject
//    private val provider: GithubRetrofitProvider = GithubRetrofitProvider()
    private val repository: GithubRepository = GithubRepository()

    //リポジトリを検索
    fun searchRepository() {
        val query = this.state.value.query
        viewModelScope.launch(Dispatchers.Main) {
            repository.searchRepository(query, 5).also { response ->
                if (response.isSuccessful) {
                    setResult(response.body()!!)
                } else {
                    Log.d("search", response.errorBody()!!.toString())
                }
            }
        }
    }

    fun setQuery(query: String) {
        val oldState = currentState()
        updateState { oldState.copy(query = query, searchResponse = oldState.searchResponse) }
    }

    private fun setResult(response: SearchedRepository) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchResponse = response) }
    }
}
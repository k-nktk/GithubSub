package com.example.githubsub.ui.screen.issuelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.model.SearchedUser
import com.example.githubsub.repository.issue.GithubIssue
import com.example.githubsub.repository.user.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueListViewModel @Inject constructor(): ViewModel(){

    private val _state = MutableStateFlow(IssueListState.initValue)
    val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> IssueListState) {
        _state.value = newState()
    }

    //API
    //Todo: Inject
//    private val provider: GithubRetrofitProvider = GithubRetrofitProvider()
    private val repository: GithubIssue = GithubIssue()

    //イシューを検索
    fun searchIssue() {
        val query = this.state.value.query
        viewModelScope.launch(Dispatchers.Main) {
            repository.searchIssue("user:$query", 10).also { response ->
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
        updateState { oldState.copy(query = query, searchedIssue = oldState.searchedIssue) }
    }

    private fun setResult(response: SearchedIssue) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedIssue = response) }
    }
}
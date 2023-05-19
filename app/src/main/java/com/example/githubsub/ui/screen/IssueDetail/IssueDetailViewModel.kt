package com.example.githubsub.ui.screen.IssueDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.SearchedIssueDetail
import com.example.githubsub.repository.issuedetail.GithubIssueDetail
import com.example.githubsub.ui.screen.issuelist.IssueListItem
import com.example.githubsub.ui.screen.issuelist.IssueListState
import com.example.githubsub.ui.screen.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(IssueDetailState.initValue)
    val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> IssueDetailState) {
        _state.value = newState()
    }
    val repository: GithubIssueDetail = GithubIssueDetail()
//    Todo: getIssueDetail

    fun getIssueDetail() {
        val result: MutableList<CommentListItem> = mutableListOf()

        viewModelScope.launch(Dispatchers.Main) {
            repository.searchIssueDetail(
                owner = "NakatsukaKyohei",
                repo = "GithubSub",
                issueNumber = 2,
                clientID = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            ).also { response ->
                if (response.isSuccessful) {
                    response.body()!!.map {
                        result.add(CommentListItem(it.body, it.user))
                    }
                    setResult(result)
                } else {
                    Log.d("search", response.errorBody()!!.toString())
                }
            }
        }
    }

    fun setResult(comments: List<CommentListItem>) {
        val oldState = currentState()
        updateState { oldState.copy(issueTitle = oldState.issueTitle, commentItems = comments) }
    }

}
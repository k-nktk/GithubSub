package com.example.githubsub.ui.screen.IssueDetail

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.Label
import com.example.githubsub.model.SearchedIssueDetail
import com.example.githubsub.model.User
import com.example.githubsub.repository.issuedetail.GithubIssueDetail
import com.example.githubsub.ui.screen.issuelist.IssueListItem
import com.example.githubsub.ui.screen.issuelist.IssueListState
import com.example.githubsub.ui.screen.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseIssueDetailViewModel: ViewModel() {
    abstract val state: StateFlow<IssueDetailState>
    abstract fun getIssueDetail(owner: String, repo: String, issueNumber: Int)
    abstract fun provideLabelColor(label: Label): Color
}

@HiltViewModel
class IssueDetailViewModel @Inject constructor(
    private val repository: GithubIssueDetail
): BaseIssueDetailViewModel() {

    private val _state = MutableStateFlow(IssueDetailState.initValue)
    override val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> IssueDetailState) {
        _state.value = newState()
    }

    override fun getIssueDetail(owner: String, repo: String, issueNumber: Int) {
        val result: MutableList<CommentListItem> = mutableListOf()

        viewModelScope.launch(Dispatchers.Main) {
            repository.searchIssueDetail(
                owner = owner,
                repo = repo,
                issueNumber = issueNumber,
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

    private fun setResult(comments: List<CommentListItem>) {
        val oldState = currentState()
        updateState { oldState.copy(commentItems = comments) }
    }


    override fun provideLabelColor(label: Label): Color {
        return Color(android.graphics.Color.parseColor("#" + label.color))
    }
}

class PreviewIssueDetailViewModel: BaseIssueDetailViewModel() {
    override val state: StateFlow<IssueDetailState> = MutableStateFlow(
            IssueDetailState(mutableListOf(CommentListItem("test_comment", User(0, "nakatsukakyohei", "https://avatars.githubusercontent.com/u/44229263?v=4"))))
    )

    override fun getIssueDetail(owner: String, repo: String, issueNumber: Int) {
        TODO("Not yet implemented")
    }

    override fun provideLabelColor(label: Label): Color {
        return Color(android.graphics.Color.parseColor("#FFFFFF"))
    }
}
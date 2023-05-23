package com.example.githubsub.ui.screen.issuelist

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.Label
import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.datastore.Result
import com.example.githubsub.repository.issue.GithubIssueRepository
import com.example.githubsub.repository.repository.GithubProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseIssueListViewModel: ViewModel() {
    abstract val state: StateFlow<IssueListState>
    abstract fun fetchIssue()
    abstract fun provideLabelColor(label: List<Label>): List<Color>
}

@HiltViewModel
class IssueListViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val projectRepository: GithubProjectRepository,
    private val issueRepository: GithubIssueRepository
): BaseIssueListViewModel() {

    private val _state = MutableStateFlow(IssueListState.initValue)
    override val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> IssueListState) {
        _state.value = newState()
    }

    //API


    //  get data from data store
    override fun fetchIssue() {
        viewModelScope.launch(Dispatchers.Main) {
            // MainUserのDataStoreからの取得
            var mainUser = ""
            withContext(Dispatchers.Default) {
                when (val result = dataStoreRepository.getUserResult()) {
                    is Result.Success -> {
                        if (!result.data.user.isNullOrEmpty()) {
                            mainUser = result.data.user
                        }
                    }
                    is Result.Error -> {
                    }
                }
            }
            Log.d("test4", mainUser)

            val result: MutableList<IssueListItem> = mutableListOf()
            issueRepository.searchIssue("user:$mainUser", 10, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).also { issueResponse ->
//                Log.d("test2", response.toString())
                if (issueResponse.isSuccessful) {
                    issueResponse.body()!!.items.map {
                        issueItem ->
                        projectRepository.searchIssueProject(issueItem.repositoryUrl, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).also {
                            repositoryResponse ->
                            if (repositoryResponse.isSuccessful) {
                                result.add(
                                    IssueListItem(
                                        issueNumber =issueItem.number,
                                        issueTitle = issueItem.title,
                                        projectTitle = repositoryResponse.body()!!.name,
                                        user = issueItem.user.login,
                                        imageUrl = issueItem.user.imageUrl,
                                        labelList = issueItem.label,
                                    )
                                )
                            }
                        }
                    }
                } else {
                    Log.e("test3", issueResponse.errorBody()!!.toString())
                }
            }
            setResult(result)
        }
    }

    private fun setResult(issueListContent: List<IssueListItem>) {
        val oldState = currentState()
//        updateState { oldState.copy(searchedIssue = issueResponse, searchedRepository = repositoryResponse) }
        updateState { oldState.copy(issueList = issueListContent) }

    }

    override fun provideLabelColor(label: List<Label>): List<Color> {
        return label.map { Color(android.graphics.Color.parseColor("#" + it.color)) }
    }
}

class PreviewIssueListViewModel() : BaseIssueListViewModel() {
    override val state: StateFlow<IssueListState> = MutableStateFlow(IssueListState(mutableListOf(
        IssueListItem(
        issueNumber = 0,
        issueTitle = "test_issue_title",
        projectTitle = "test_repository_title",
        user = "test_user_name",
        imageUrl = "",
        labelList = mutableListOf(Label(id = 0, name = "bug", color = "FFFFFF"))
        )
    )))

    override fun fetchIssue() {
        TODO("Not yet implemented")
    }

    override fun provideLabelColor(label: List<Label>): List<Color> {
        return mutableListOf(Color(android.graphics.Color.parseColor("#FFFFFF")))
    }

}


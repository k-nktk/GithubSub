package com.example.githubsub.ui.screen.issuelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.model.SearchedIssue
import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.datastore.Result
import com.example.githubsub.repository.issue.GithubIssue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IssueListViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel(){

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


    //  get data from data store
    fun fetchIssue() {
        val oldState = currentState()


        viewModelScope.launch(Dispatchers.Main) {
            // MainUserのDataStoreからの取得
            var query: String = ""
            //                            updateState { oldState.copy(query = oldState.query, searchedIssue = oldState.searchedIssue, mainUser = result.data.user) }
            withContext(Dispatchers.Default) {
                when (val result = dataStoreRepository.getUserResult()) {
                    is Result.Success -> {
                        if (!result.data.user.isNullOrEmpty()) {
//                            updateState { oldState.copy(query = oldState.query, searchedIssue = oldState.searchedIssue, mainUser = result.data.user) }
                            query = result.data.user
                        } else {
                            updateState {
                                oldState.copy(
                                    searchedIssue = oldState.searchedIssue,
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        updateState {
                            oldState.copy(
                                searchedIssue = oldState.searchedIssue,
                            )
                        }
                    }
                }
            }

            Log.d("test4", query)
            repository.searchIssue("user:$query", 10).also { response ->
                Log.d("test2", response.toString())
                if (response.isSuccessful) {
                    setResult(response.body()!!)
                } else {
                    Log.e("test3", response.errorBody()!!.toString())
                }
            }
        }
        Log.d("fetchMainUser", "is finish")

    }

    private fun setResult(response: SearchedIssue) {
        val oldState = currentState()
        updateState { oldState.copy(searchedIssue = response) }
    }
}
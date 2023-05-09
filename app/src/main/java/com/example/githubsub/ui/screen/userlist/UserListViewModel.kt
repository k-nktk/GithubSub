package com.example.githubsub.ui.screen.userlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.model.SearchedUser
import com.example.githubsub.repository.user.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(): ViewModel(){

    private val _state = MutableStateFlow(UserListState.initValue)
    val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> UserListState) {
        _state.value = newState()
    }

    //API
    //Todo: Inject
//    private val provider: GithubRetrofitProvider = GithubRetrofitProvider()
    private val repository: GithubUser = GithubUser()

    //リポジトリを検索
    fun searchUser() {
        val query = this.state.value.query
        viewModelScope.launch(Dispatchers.Main) {
            repository.searchUser(query, 5).also { response ->
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
        updateState { oldState.copy(query = query, searchedUser = oldState.searchedUser) }
    }

    private fun setResult(response: SearchedUser) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedUser = response) }
    }
}
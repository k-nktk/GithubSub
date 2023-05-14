package com.example.githubsub.ui.screen.userlist

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.Settings
import com.example.githubsub.model.SearchedUser
import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.user.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel(){

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

    //  push data to proto data store
    fun pushMainUser() {
        viewModelScope.launch(Dispatchers.Main) {
            dataStoreRepository.writeUserResult(state.value.query)
        }
    }


    //  get data from data store
    fun fetchMainUser() {
        val oldState = currentState()
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = dataStoreRepository.getUserResult()) {
                is com.example.githubsub.repository.datastore.Result.Success -> {
                    if (!result.data.user.isNullOrEmpty()) {
                        updateState { oldState.copy(query = oldState.query, searchedUser = oldState.searchedUser, mainUser = result.data.user) }
                    } else {
                        updateState { oldState.copy(query = oldState.query, searchedUser = oldState.searchedUser, mainUser = "ユーザーが登録されていません。") }
                    }
                }
                is com.example.githubsub.repository.datastore.Result.Error -> {
                    updateState { oldState.copy(query = oldState.query, searchedUser = oldState.searchedUser, mainUser = "Error") }

                }
            }
        }
    }

// UI
    fun setQuery(query: String) {
        val oldState = currentState()
        updateState { oldState.copy(query = query, searchedUser = oldState.searchedUser, mainUser = oldState.mainUser) }
//    Todo: change copy(const) to copy(proceeding = true)
    }

    private fun setResult(response: SearchedUser) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedUser = response, mainUser = oldState.mainUser) }
    }

    fun setMainUser(mainUser: String) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedUser = oldState.searchedUser, mainUser = mainUser) }

    }
}
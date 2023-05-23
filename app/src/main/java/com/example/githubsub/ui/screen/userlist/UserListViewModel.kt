package com.example.githubsub.ui.screen.userlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.SearchUserItem
import com.example.githubsub.model.SearchedUser
import com.example.githubsub.repository.datastore.DataStoreRepository
import com.example.githubsub.repository.user.GithubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseUserListViewModel: ViewModel() {
    abstract val state: StateFlow<UserListState>
    abstract fun searchUser()
    abstract fun pushMainUser()
    abstract fun fetchMainUser()
    // UI
    abstract fun setQuery(query: String)
    abstract fun setMainUser(mainUser: String)
}

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val repository: GithubUser
): BaseUserListViewModel(){

    private val _state = MutableStateFlow(UserListState.initValue)
    override val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> UserListState) {
        _state.value = newState()
    }

    override fun searchUser() {
        val query = this.state.value.query
        viewModelScope.launch(Dispatchers.Main) {
            repository.searchUser(query, 5, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).also { response ->
                if (response.isSuccessful) {
                    setResult(response.body()!!)
                } else {
                    Log.d("search", response.errorBody()!!.toString())
                }
            }
        }
    }

    //  push data to proto data store
    override fun pushMainUser() {
        viewModelScope.launch(Dispatchers.Main) {
            dataStoreRepository.writeUserResult(state.value.query)
        }
    }


    //  get data from data store
    override fun fetchMainUser() {
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
    override fun setQuery(query: String) {
        val oldState = currentState()
        updateState { oldState.copy(query = query, searchedUser = oldState.searchedUser, mainUser = oldState.mainUser) }
    //    Todo: change copy(const) to copy(proceeding = true)
    }

    private fun setResult(response: SearchedUser) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedUser = response, mainUser = oldState.mainUser) }
    }

    override fun setMainUser(mainUser: String) {
        val oldState = currentState()
        updateState { oldState.copy(query = oldState.query, searchedUser = oldState.searchedUser, mainUser = mainUser) }
    }
}

class PreviewUserListViewModel : BaseUserListViewModel() {
    override val state: StateFlow<UserListState> = MutableStateFlow(
        UserListState(
            query = "test_query",
            searchedUser = SearchedUser(mutableListOf(SearchUserItem(
                id = 0,
                name = "test_user_name",
                imageUrl = ""
            ))),
            mainUser = "test_main_user",
        )
    )

    override fun searchUser() {
        TODO("Not yet implemented")
    }

    override fun pushMainUser() {
        TODO("Not yet implemented")
    }

    override fun fetchMainUser() {
        TODO("Not yet implemented")
    }

    override fun setQuery(query: String) {
        TODO("Not yet implemented")
    }

    override fun setMainUser(mainUser: String) {
        TODO("Not yet implemented")
    }

}
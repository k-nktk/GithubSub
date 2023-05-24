package com.example.githubsub.ui.screen.userlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.UserItem
import com.example.githubsub.model.UserList
import com.example.githubsub.repository.datastore.settings.SettingsRepository
import com.example.githubsub.repository.datastore.settings.Result
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
    private val dataStoreRepository: SettingsRepository,
    private val repository: GithubUser
): BaseUserListViewModel(){

    private val _state = MutableStateFlow(UserListState.initValue)
    override val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> UserListState) {
        _state.value = newState()
    }

    override fun searchUser() {
        val query = this.state.value.userName
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
            dataStoreRepository.writeUserResult(state.value.userName)
        }
    }


    //  get data from data store
    override fun fetchMainUser() {
        val oldState = currentState()
        viewModelScope.launch(Dispatchers.Main) {
            when(val result = dataStoreRepository.getUserResult()) {
                is Result.Success -> {
                    if (!result.data.user.isNullOrEmpty()) {
                        updateState { oldState.copy(userName = oldState.userName, userList = oldState.userList, mainUser = result.data.user) }
                    } else {
                        updateState { oldState.copy(userName = oldState.userName, userList = oldState.userList, mainUser = "ユーザーが登録されていません。") }
                    }
                }
                is Result.Error -> {
                    updateState { oldState.copy(userName = oldState.userName, userList = oldState.userList, mainUser = "Error") }

                }
            }
        }
    }

    // UI
    override fun setQuery(query: String) {
        val oldState = currentState()
        updateState { oldState.copy(userName = query, userList = oldState.userList, mainUser = oldState.mainUser) }
    //    Todo: change copy(const) to copy(proceeding = true)
    }

    private fun setResult(response: UserList) {
        val oldState = currentState()
        updateState { oldState.copy(userName = oldState.userName, userList = response, mainUser = oldState.mainUser) }
    }

    override fun setMainUser(mainUser: String) {
        val oldState = currentState()
        updateState { oldState.copy(userName = oldState.userName, userList = oldState.userList, mainUser = mainUser) }
    }
}

class PreviewUserListViewModel : BaseUserListViewModel() {
    override val state: StateFlow<UserListState> = MutableStateFlow(
        UserListState(
            userName = "test_query",
            userList = UserList(mutableListOf(UserItem(
                id = 0,
                login = "test_user_name",
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
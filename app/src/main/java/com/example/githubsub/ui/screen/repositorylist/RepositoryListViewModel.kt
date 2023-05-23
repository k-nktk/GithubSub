package com.example.githubsub.ui.screen.repositorylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.BuildConfig
import com.example.githubsub.model.ProjectList
import com.example.githubsub.repository.repository.GithubProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseRepositoryListViewModel: ViewModel() {
    abstract val state: StateFlow<RepositoryListState>
    abstract fun searchRepository()
    abstract fun setQuery(query: String)
}

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val repository: GithubProjectRepository
): BaseRepositoryListViewModel() {

    private val _state = MutableStateFlow(RepositoryListState.initValue)
    override val state = _state.asStateFlow()

    private fun currentState() = _state.value
    private fun updateState(newState: () -> RepositoryListState) {
        _state.value = newState()
    }

    //リポジトリを検索
    override fun searchRepository() {
        val query = this.state.value.projectName
        viewModelScope.launch(Dispatchers.Main) {
            repository.searchProject(query, 5, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).also { response ->
                if (response.isSuccessful) {
                    setResult(response.body()!!)
                } else {
                    Log.d("search", response.errorBody()!!.toString())
                }
            }
        }
    }

    override fun setQuery(query: String) {
        val oldState = currentState()
        updateState { oldState.copy(projectName = query, projectList = oldState.projectList) }
    }

    private fun setResult(response: ProjectList) {
        val oldState = currentState()
        updateState { oldState.copy(projectName = oldState.projectName, projectList = response) }
    }
}

class PreviewRepositoryListViewModel() : BaseRepositoryListViewModel() {

    override val state: StateFlow<RepositoryListState> = MutableStateFlow(
        RepositoryListState("testQuery", ProjectList(mutableListOf()))
    )
    override fun searchRepository() {
        TODO("Not yet implemented")
    }

    override fun setQuery(query: String) {
        TODO("Not yet implemented")
    }

}
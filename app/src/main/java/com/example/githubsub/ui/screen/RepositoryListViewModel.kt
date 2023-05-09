package com.example.githubsub.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsub.model.SearchResponse
import com.example.githubsub.repository.GithubRepository
import com.example.githubsub.repository.GithubRetrofitProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(): ViewModel(){
    init {
        viewModelScope.launch {

        }
    }
    private var _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String> get() = _query

    //検索結果
    private var _searchResult = MutableLiveData<SearchResponse> {
        runCatching { repository.searchRepository(query = query) }
            .onSuccess { this.searchResult. }
            .onFailure {  }
    }
    val searchResult: LiveData<Result<SearchResponse>> get() = _searchResult

    //API
    private val provider: GithubRetrofitProvider = GithubRetrofitProvider()
    private val repository: GithubRepository = GithubRepository(provider.retrofit)
    //リポジトリを検索
    fun searchRepository(query: String) {
        viewModelScope.launch {
            kotlin.runCatching { repository.searchRepository(query) }
                .onSuccess {  }
                .onFailure {  }
            val result = withContext(Dispatchers.Default) {
                repository.searchRepository(query).also { response ->
                    if (response.isSuccessful) {
                        return@withContext Result.success(response.body()!!)
                    } else {
                        return@withContext Result.failure<Throwable>(Throwable(response.errorBody()!!.toString()))
                    }
                }
            }

        }
    }

    fun setQuery(query: String) {
        this._query.postValue(query)
    }
}
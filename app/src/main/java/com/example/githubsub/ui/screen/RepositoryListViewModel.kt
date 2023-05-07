package com.example.githubsub.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsub.repository.GithubRepository
import com.example.githubsub.repository.GithubRetrofitProvider
import com.example.githubsub.repository.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(): ViewModel(){

    //検索結果
    private var _searchResult: MutableLiveData<Result<SearchResponse>> = MutableLiveData()
    val searchResult: LiveData<Result<SearchResponse>> get() = _searchResult

    //API
    private val provider: GithubRetrofitProvider = GithubRetrofitProvider()
    private val repository: GithubRepository = GithubRepository(provider.retrofit)

    //リポジトリを検索
    fun searchRepository(query: String) {
        repository.searchRepository(query).also { response ->
            if (response.isSuccessful) {
                this._searchResult.postValue(Result.success(response.body()!!))
            } else {
                this._searchResult.postValue(Result.failure(Throwable(response.errorBody()!!.toString())))
            }
        }
    }
}
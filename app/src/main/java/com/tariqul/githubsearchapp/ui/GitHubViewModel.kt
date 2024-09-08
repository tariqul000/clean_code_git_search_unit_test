package com.tariqul.githubsearchapp.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tariqul.githubsearchapp.data.model.GitHubRepo
import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
import com.tariqul.githubsearchapp.data.remote.Resource
import com.tariqul.githubsearchapp.utils.NetworkUtil


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class GitHubViewModel @Inject constructor(
    private val repository: GitHubRepoRepository,
) : ViewModel() {

    private val _repoList = MutableLiveData<List<GitHubRepo>>()
    val repoList: LiveData<List<GitHubRepo>> = _repoList

//    private val _repositories = MutableLiveData<List<GitHubRepo>>()
//    val repositories: LiveData<List<GitHubRepo>> = _repositories


//    private val _isNetworkAvailable = MutableLiveData<Boolean>()
//    val isNetworkAvailable: LiveData<Boolean> get() = _isNetworkAvailable
//
//    init {
//        _isNetworkAvailable.postValue(networkUtil.isConnected())
//
//        networkUtil.observeNetworkState().observeForever {
//            _isNetworkAvailable.postValue(it)
//        }
//    }

    fun searchRepos(query: String) {

        viewModelScope.launch {
            delay(500)  // Debounce search to prevent excessive calls
            if (query.isNotEmpty()) {
                val result = repository.searchRepos(query)
                if (result is Resource.Success) {
                    _repoList.value = result.data?.items
                } else if (result is Resource.Error) {
                    // Handle the error
                    Log.e("GitHubViewModel", "Error: ${result.message}")
                }
            }
        }
    }




}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    return data as T
}
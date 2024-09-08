package com.tariqul.githubsearchapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tariqul.githubsearchapp.data.model.GitHubRepo
import com.tariqul.githubsearchapp.data.model.GitHubRepoResponse
import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
import com.tariqul.githubsearchapp.data.remote.Resource
import com.tariqul.githubsearchapp.ui.GitHubViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Mock
    lateinit var repository: GitHubRepoRepository

    @Mock
    lateinit var observer: Observer<List<GitHubRepo>>

    private lateinit var viewModel: GitHubViewModel

    @Before
    fun setUp() {
        viewModel = GitHubViewModel(repository)
    }

    @Test
    fun `searchRepositories updates live data successfully`() = runBlockingTest {
        val query = "android"
        val repositoryList = listOf(GitHubRepo(121,"Repo1","description","html_url"), GitHubRepo(121,"Repo1","description","html_url"))
        Mockito.`when`(repository.searchRepos("query").data?.items)
            .thenReturn(repositoryList)
        viewModel.repoList.observeForever(observer)
        viewModel.searchRepos(query)

        Mockito.verify(observer).onChanged(repositoryList)
        assertEquals(repositoryList, viewModel.repoList.value)
    }
}

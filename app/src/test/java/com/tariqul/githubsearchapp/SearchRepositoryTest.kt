package com.tariqul.githubsearchapp

import com.tariqul.githubsearchapp.data.model.GitHubRepo
import com.tariqul.githubsearchapp.data.model.GitHubRepoResponse
import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
import com.tariqul.githubsearchapp.data.remote.GitHubService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    @Mock
    lateinit var apiService: GitHubService

    private lateinit var repository: GitHubRepoRepository

    @Before
    fun setUp() {
        repository = GitHubRepoRepository(apiService)
    }

    @Test
    fun `searchRepositories returns a list of repositories from API`() = runBlockingTest {
        val query = "android"
        val repositoryList = listOf(GitHubRepo(121,"Repo1","description","html_url"), GitHubRepo(121,"Repo1","description","html_url"))
        Mockito.`when`(apiService.searchRepositories(query).body()?.items).thenReturn(repositoryList)

        val result = repository.searchRepos(query)
        assertEquals(repositoryList, result)
    }

    @Test
    fun `searchRepositories handles empty response`() = runBlockingTest {
        val query = "android"
        Mockito.`when`(apiService.searchRepositories(query)).thenReturn(Response.success(
            GitHubRepoResponse(emptyList())
        ))

        val result = repository.searchRepos(query)
        result.data?.items?.isEmpty()?.let { assertTrue(it) }
    }
}

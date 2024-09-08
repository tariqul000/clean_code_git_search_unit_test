import com.tariqul.githubsearchapp.data.model.GitHubRepo
import com.tariqul.githubsearchapp.data.model.GitHubRepoResponse
import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
import com.tariqul.githubsearchapp.data.remote.GitHubService
import okhttp3.ResponseBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.runner.RunWith
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    // Mock the API service
    private lateinit var apiService: GitHubService
    private lateinit var repository: GitHubRepoRepository

    @Before
    fun setUp() {
        // Initialize mock API service
        apiService = mock(GitHubService::class.java)
        // Initialize repository with the mocked API service
        repository = GitHubRepoRepository(apiService)
    }

    @Test
    fun `searchRepositories returns a list of repositories from API`() = runBlockingTest {
        // Define the search query
        val query = "android"
        // Create a mock response
        val repositoryList = listOf(
            GitHubRepo(121, "Description1", "url1", "Kotlin"),
            GitHubRepo(121, "Description2", "url2", "Kotlin2")
        )
        val searchResponse = GitHubRepoResponse(items = repositoryList)

        // Simulate the API service returning a successful response
        `when`(apiService.searchRepositories(query)).thenReturn(Response.success(searchResponse))

        // Call the repository's search function
        val result = repository.searchRepos(query)

        // Verify that the API service was called with the correct query
        verify(apiService).searchRepositories(query)

        // Assert that the result matches the mock response
        assertEquals(repositoryList, result)
    }

    @Test
    fun `searchRepositories returns an empty list when no repositories found`() = runBlockingTest {
        // Define the search query
        val query = "nonexistent"
        // Create an empty response
        val searchResponse = GitHubRepoResponse(items = emptyList())

        // Simulate the API service returning an empty result
        `when`(apiService.searchRepositories(query)).thenReturn(Response.success(searchResponse))

        // Call the repository's search function
        val result = repository.searchRepos(query)

        // Verify that the API service was called with the correct query
        verify(apiService).searchRepositories(query)

        // Assert that the result is an empty list
        result.data?.items?.isEmpty()?.let { assertTrue(it) }
    }

    @Test
    fun `searchRepositories handles API failure`() = runBlockingTest {
        // Define the search query
        val query = "android"

        // Simulate a failed API response
        `when`(apiService.searchRepositories(query)).thenReturn(Response.error(500, mock(
            ResponseBody::class.java)))

        // Call the repository's search function
        val result = repository.searchRepos(query)

        // Verify that the API service was called with the correct query
        verify(apiService).searchRepositories(query)

        // Assert that the result is an empty list due to failure
        result.data?.items?.isEmpty()?.let { assertTrue(it) }
    }
}

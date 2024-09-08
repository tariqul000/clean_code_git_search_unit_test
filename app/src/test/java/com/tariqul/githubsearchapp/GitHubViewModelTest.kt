//package com.tariqul.githubsearchapp
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.MutableLiveData
//import com.tariqul.githubsearchapp.data.model.GitHubRepo
//import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
//import com.tariqul.githubsearchapp.ui.GitHubViewModel
//import com.tariqul.githubsearchapp.ui.getOrAwaitValue
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito
//import org.mockito.Mockito.mock
//
//@ExperimentalCoroutinesApi
//class GitHubViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: GitHubViewModel
//    private lateinit var repository: GitHubRepoRepository
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//        repository = mock(GitHubRepoRepository::class.java)
//        viewModel = GitHubViewModel(repository)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun `searchRepositories should fetch data correctly`() = runBlockingTest {
//        val repositories = listOf(GitHubRepo(121, "Description1", "url1", "Kotlin"))
//
//        // Mock the repository call
//        Mockito.`when`(repository.searchRepos("query").data?.items)
//            .thenReturn(repositories)
//
//        viewModel.searchRepos("query")
//
//        assert(viewModel.repoList.getOrAwaitValue().size == 1)
//        assert(viewModel.repoList.getOrAwaitValue()[0].name == "Repo1")
//    }
//}

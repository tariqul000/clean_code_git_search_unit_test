package com.tariqul.githubsearchapp.data.remote

import com.tariqul.githubsearchapp.data.model.GitHubRepoResponse
import javax.inject.Inject

class GitHubRepoRepository @Inject constructor(
    private val gitHubService: GitHubService
) {
    suspend fun searchRepos(query: String): Resource<GitHubRepoResponse> {
        return try {
            val response = gitHubService.searchRepositories(query)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
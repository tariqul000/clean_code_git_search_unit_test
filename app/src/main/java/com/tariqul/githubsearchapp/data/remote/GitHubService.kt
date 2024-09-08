package com.tariqul.githubsearchapp.data.remote

import com.tariqul.githubsearchapp.data.model.GitHubRepoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String
    ): Response<GitHubRepoResponse>
}
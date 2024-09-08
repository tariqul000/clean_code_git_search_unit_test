package com.tariqul.githubsearchapp.data.model

data class GitHubRepoResponse(
    val items: List<GitHubRepo>
)

data class GitHubRepo(
    val id: Long,
    val name: String,
    val description: String?,
    val html_url: String
)
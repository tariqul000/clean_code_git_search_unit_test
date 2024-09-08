package com.tariqul.githubsearchapp.di

import android.content.Context
import com.tariqul.githubsearchapp.data.remote.GitHubService
import com.tariqul.githubsearchapp.data.remote.GitHubRepoRepository
import com.tariqul.githubsearchapp.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitHubApi(): GitHubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubRepository(api: GitHubService): GitHubRepoRepository {
        return GitHubRepoRepository(api)
    }

    @Provides
    @Singleton
    fun provideNetworkUtil(
        @ApplicationContext context: Context
    ): NetworkUtil {
        return NetworkUtil(context)
    }
}

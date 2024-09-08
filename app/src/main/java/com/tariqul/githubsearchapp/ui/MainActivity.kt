package com.tariqul.githubsearchapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tariqul.githubsearchapp.R
import com.tariqul.githubsearchapp.databinding.ActivityMainBinding
import com.tariqul.githubsearchapp.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GitHubViewModel by viewModels()
    private var searchJob: Job? = null

    @Inject
    lateinit var networkUtil: NetworkUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = RepoAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        // Observe the repo list from ViewModel
        viewModel.repoList.observe(this, Observer { repos ->
            repos?.let {
                binding.recyclerView.adapter = RepoAdapter(it)
                binding.progress.visibility = View.GONE
            }
        })

        // Handle search bar input
        binding.searchBar.addTextChangedListener { text ->
            text?.let {
                if (networkUtil.isConnected()) {
                    binding.progress.visibility = View.VISIBLE
                    searchJob?.cancel()
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(100) // Delay for throttling
                        viewModel.searchRepos(it.toString())
                    }
                } else {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
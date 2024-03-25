package com.haritsbachtiar.cryptoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haritsbachtiar.cryptoapp.databinding.ActivityMainBinding
import com.haritsbachtiar.cryptoapp.presentation.CryptoAdapter
import com.haritsbachtiar.cryptoapp.presentation.CryptoFeedUiState
import com.haritsbachtiar.cryptoapp.presentation.CryptoFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CryptoFeedViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CryptoFeedViewModel::class.java)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.cryptoFeedUiState.collect {
                when (it) {
                    is CryptoFeedUiState.NoCryptoFeed -> {
                        binding.tvState.text = "CRYPTO EMPTY"
                        binding.tvState.isVisible = true
                        binding.rvCrypto.isVisible = false
                    }

                    is CryptoFeedUiState.HasCryptoFeed -> {
                        with(binding) {
                            tvState.isVisible = false
                            rvCrypto.isVisible = true
                            rvCrypto.apply {
                                setHasFixedSize(true)
                                layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    RecyclerView.VERTICAL,
                                    false
                                )
                                adapter = CryptoAdapter(items = it.cryptoFeeds)
                            }
                        }

                    }

                    is CryptoFeedUiState.Error -> {
                        binding.tvState.text = "REQUEST ERROR"
                        binding.tvState.isVisible = true
                        binding.rvCrypto.isVisible = false
                    }

                    is CryptoFeedUiState.Loading -> {
                        binding.tvState.text = "Loading . . ."
                        binding.tvState.isVisible = true
                        binding.rvCrypto.isVisible = false
                    }

                    is CryptoFeedUiState.Nothing -> Unit
                }
            }
        }
    }
}
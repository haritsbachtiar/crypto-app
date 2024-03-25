package com.haritsbachtiar.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haritsbachtiar.cryptoapp.domain.Connectivity
import com.haritsbachtiar.cryptoapp.domain.CryptoFeedResult
import com.haritsbachtiar.cryptoapp.domain.CryptoFeedUseCase
import com.haritsbachtiar.cryptoapp.domain.InvalidData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoFeedViewModel @Inject constructor(
    private val cryptoFeedUseCase: CryptoFeedUseCase
) : ViewModel(), CryptoFeedItemViewModel {
    private val state = MutableStateFlow(
        CryptoFeedViewModelState(isLoading = true, failed = "")
    )

    val cryptoFeedUiState = state
        .map(CryptoFeedViewModelState::toCryptoFeedUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = state.value.toCryptoFeedUiState()
        )

    init {
        loadCryptoFeed()
    }

    fun loadCryptoFeed() {
        viewModelScope.launch {
            cryptoFeedUseCase.loadCrypto().collect { result ->
                state.update {
                    when (result) {
                        is CryptoFeedResult.Success -> it.copy(
                            cryptoFeeds = result.cryptoFeedItems,
                            isLoading = false
                        )

                        is CryptoFeedResult.Failure -> it.copy(
                            isLoading = false,
                            failed = when (result.throwable) {
                                is Connectivity -> "Connectivity"
                                is InvalidData -> "Invalid Data"
                                else -> "Something Went Wrong"
                            },
                        )

                        is CryptoFeedResult.Loading -> it.copy(
                            isLoading = true,
                        )
                    }
                }

            }
        }
    }
}
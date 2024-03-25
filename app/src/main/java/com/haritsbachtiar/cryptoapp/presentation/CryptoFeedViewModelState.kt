package com.haritsbachtiar.cryptoapp.presentation

import com.haritsbachtiar.cryptoapp.domain.CryptoFeedItem

data class CryptoFeedViewModelState(
    val cryptoFeeds: List<CryptoFeedItem> = emptyList(),
    val isLoading: Boolean = false,
    val failed: String = ""
) {
    fun toCryptoFeedUiState(): CryptoFeedUiState =
        when {
            isLoading -> CryptoFeedUiState.Loading
            failed.isNotEmpty() -> CryptoFeedUiState.Error
            cryptoFeeds.isEmpty() -> CryptoFeedUiState.NoCryptoFeed
            else ->CryptoFeedUiState.HasCryptoFeed(cryptoFeeds = cryptoFeeds)
        }
}
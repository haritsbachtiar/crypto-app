package com.haritsbachtiar.cryptoapp.presentation

import com.haritsbachtiar.cryptoapp.domain.CryptoFeedItem

sealed class CryptoFeedUiState {

    data class HasCryptoFeed(
        val cryptoFeeds: List<CryptoFeedItem>,
    ) : CryptoFeedUiState()

    object NoCryptoFeed : CryptoFeedUiState()
    object Loading : CryptoFeedUiState()
    object Error : CryptoFeedUiState()
    object Nothing : CryptoFeedUiState()
}
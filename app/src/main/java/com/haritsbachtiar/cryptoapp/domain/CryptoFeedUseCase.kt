package com.haritsbachtiar.cryptoapp.domain

import kotlinx.coroutines.flow.Flow

interface CryptoFeedUseCase {
    fun loadCrypto(): Flow<CryptoFeedResult>
}

sealed class CryptoFeedResult {
    data class Success(val cryptoFeedItems: List<CryptoFeedItem>) : CryptoFeedResult()
    data class Failure(val throwable: Throwable) : CryptoFeedResult()
    object Loading : CryptoFeedResult()
}
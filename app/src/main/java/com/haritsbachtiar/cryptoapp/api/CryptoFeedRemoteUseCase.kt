package com.haritsbachtiar.cryptoapp.api

import kotlinx.coroutines.flow.Flow

interface CryptoFeedRemoteUseCase {
    fun getCryptoFeed(): Flow<HttpClientResult>
}
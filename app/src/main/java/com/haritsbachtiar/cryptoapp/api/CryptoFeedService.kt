package com.haritsbachtiar.cryptoapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoFeedService {
    @GET("data/top/totaltoptiervolfull")
    suspend fun getCryptoFeed(
        @Query("limit") limit: Int? = 20,
        @Query("tsym") tsym: String? = "USD"
    ): RemoteCryptoFeed
}
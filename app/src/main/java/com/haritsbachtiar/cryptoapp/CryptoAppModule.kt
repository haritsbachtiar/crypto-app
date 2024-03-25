package com.haritsbachtiar.cryptoapp

import com.haritsbachtiar.cryptoapp.api.CryptoFeedRemoteUseCase
import com.haritsbachtiar.cryptoapp.api.CryptoFeedRemoteUseCaseImpl
import com.haritsbachtiar.cryptoapp.api.CryptoFeedService
import com.haritsbachtiar.cryptoapp.domain.CryptoFeedUseCase
import com.haritsbachtiar.cryptoapp.domain.CryptoFeedUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CryptoAppModule {
    @Provides
    @Singleton
    fun provideCryptoFeedService(retrofit: Retrofit): CryptoFeedService =
        retrofit.create(CryptoFeedService::class.java)

    @Provides
    @Singleton
    fun provideCryptoFeedRemoteUseCase(cryptoFeedService: CryptoFeedService): CryptoFeedRemoteUseCase =
        CryptoFeedRemoteUseCaseImpl(cryptoFeedService)

    @Provides
    @Singleton
    fun provideCryptoFeedUseCase(cryptoFeedRemoteUseCase: CryptoFeedRemoteUseCase): CryptoFeedUseCase =
        CryptoFeedUseCaseImpl(cryptoFeedRemoteUseCase)
}
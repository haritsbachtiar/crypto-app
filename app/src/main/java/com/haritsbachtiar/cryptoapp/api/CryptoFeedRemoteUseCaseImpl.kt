package com.haritsbachtiar.cryptoapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoFeedRemoteUseCaseImpl @Inject constructor(
    private val cryptoFeedService: CryptoFeedService
) : CryptoFeedRemoteUseCase {
    override fun getCryptoFeed(): Flow<HttpClientResult> = flow {
        try {
            emit(HttpClientResult.Success(cryptoFeedService.getCryptoFeed()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    emit(HttpClientResult.Failure(ConnectivityException()))
                }

                is HttpException -> {
                    if (throwable.code() == 422) {
                        emit(HttpClientResult.Failure(InvalidDataException()))
                    }
                }

                else -> {
                    emit(HttpClientResult.Failure(InvalidDataException()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}
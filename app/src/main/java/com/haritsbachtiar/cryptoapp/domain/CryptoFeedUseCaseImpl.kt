package com.haritsbachtiar.cryptoapp.domain

import android.util.Log
import com.haritsbachtiar.cryptoapp.api.ConnectivityException
import com.haritsbachtiar.cryptoapp.api.CryptoFeedRemoteUseCase
import com.haritsbachtiar.cryptoapp.api.HttpClientResult
import com.haritsbachtiar.cryptoapp.api.InvalidDataException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CryptoFeedUseCaseImpl @Inject constructor(
    private val cryptoRemoteUseCase: CryptoFeedRemoteUseCase
) : CryptoFeedUseCase {

    override fun loadCrypto(): Flow<CryptoFeedResult> = flow {
        cryptoRemoteUseCase.getCryptoFeed().collect { result ->
            when (result) {
                is HttpClientResult.Success -> {
                    val cryptoFeed = result.root.data
                    if (cryptoFeed.isNotEmpty()) {
                        emit(CryptoFeedResult.Success(CryptoFeedMapper.map(cryptoFeed)))
                    } else {
                        emit(CryptoFeedResult.Success(emptyList()))
                    }
                }

                is HttpClientResult.Failure -> {
                    Log.d("loadCryptoFeed", "Failure")
                    when (result.throwable) {
                        is ConnectivityException -> {
                            emit(CryptoFeedResult.Failure(Connectivity()))
                        }

                        is InvalidDataException -> {
                            Log.d("loadCryptoFeed", "InvalidData")
                            emit(CryptoFeedResult.Failure(InvalidData()))
                        }
                    }
                }
            }
        }
    }.onStart {
        emit(CryptoFeedResult.Loading)
        delay(1000)
    }
}

class InvalidData : Throwable()
class Connectivity : Throwable()
package com.haritsbachtiar.cryptoapp.api

sealed class HttpClientResult {
    data class Success(val root: RemoteCryptoFeed) : HttpClientResult()
    data class Failure(val throwable: Throwable) : HttpClientResult()
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()